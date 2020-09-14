package com.raylabz.cashew;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.raylabz.cashew.exception.NoCacheException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Allows access to the caches using simple commands.
 */
public final class Cashew {

    /**
     * A map of classes to caches which allows objects to be stored according to their types.
     */
    private static final HashMap<Class<?>, StringCache<?>> OBJECT_CACHES = new HashMap<>();

    /**
     * A map of string-named caches.
     */
    private static final HashMap<String, StringCache<?>> GENERIC_CACHES = new HashMap<>();

    /**
     * The main cache, used for storing any type of object.
     */
    private static final StringCache<Object> MAIN_CACHE = new StringCache<>(Cache.DEFAULT_TIME_TO_LIVE, Cache.DEFAULT_CLEANUP_INTERVAL);

    /**
     * The default backup task interval - once per 24 hours.
     */
    private static final long DEFAULT_BACKUP_TASK_INTERVAL = 24 * 60 * 60 * 1000L;

    /**
     * Cashew's backup task interval - in milliseconds.
     */
    private static long backupTaskInterval = DEFAULT_BACKUP_TASK_INTERVAL;

    /**
     * A background thread, responsible for backing up data in the caches every backupTaskInterval.
     */
    private static final Thread BACKGROUND_BACKUP_THREAD;

    /**
     * The date format, human-readable.
     */
    public static final SimpleDateFormat SIMPLE_HUMAN_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    /**
     * The date format used in backup files.
     */
    public static final SimpleDateFormat DATE_FILE_FORMAT = new SimpleDateFormat("dd-MM-yyyy-HH-mm");

    static {

        BACKGROUND_BACKUP_THREAD = new Thread(() -> {

            while (true) {

                try {
                    Thread.sleep(backupTaskInterval);
                } catch (InterruptedException e) {
                    System.err.println("CASHEW ERROR -> Backup Thread interrupted while waiting.");
                    e.printStackTrace();
                }

                System.out.println("CASHEW BACKUP IS NOW RUNNING...");

                System.out.println("Locating caches...");
                System.out.println("Found " + GENERIC_CACHES.size() + " generic caches.");
                System.out.println("Found " + OBJECT_CACHES.size() + " POJO caches.");
                System.out.println("Found main cache.");
                System.out.println("Caches located successfully.");

                System.out.println();

                System.out.println("Listing generic caches...");
                for (Map.Entry<String, StringCache<?>> entry : GENERIC_CACHES.entrySet()) {
                    final String cacheName = entry.getKey();
                    System.out.println(" -> " + cacheName);
                }

                System.out.println("Listing POJO caches...");
                for (Map.Entry<Class<?>, StringCache<?>> entry : OBJECT_CACHES.entrySet()) {
                    final String cacheName = entry.getKey().getName();
                    System.out.println(" -> " + cacheName + "(class)");
                }

                System.out.println();

                System.out.println("Beginning backup process");

                try {
                    System.out.println("Backing up main cache...");
                    final String mainCacheBackupFilename = backupMainCache();
                    System.out.println("Main cache successfully backed up in file '" + mainCacheBackupFilename + "'");

                    System.out.println();

                    System.out.println("Backing up generic caches...");
                    for (Map.Entry<String, StringCache<?>> entry : GENERIC_CACHES.entrySet()) {
                        final String cacheName = entry.getKey();
                        final String genericCacheBackupFilename = backupCache(entry.getValue(), cacheName);
                        System.out.println("Generic cache successfully backed up in file '" + genericCacheBackupFilename + "'");
                    }

                    System.out.println();

                    System.out.println("Backing up POJO caches...");
                    for (Map.Entry<Class<?>, StringCache<?>> entry : OBJECT_CACHES.entrySet()) {
                        final String cacheName = entry.getKey().getName();
                        final String pojoCacheBackupFilename = backupCache(entry.getValue(), cacheName);
                        System.out.println("POJO cache successfully backed up in file '" + pojoCacheBackupFilename + "'");
                    }

                    System.out.println();
                    System.out.println("Backup process completed successfully.");
                    final long nextBackupTime = System.currentTimeMillis() + backupTaskInterval;
                    System.out.println("Next backup at " + SIMPLE_HUMAN_FORMAT.format(new Date(nextBackupTime)));

                } catch (IOException e) {
                    System.out.println();
                    System.err.println("Backup process failed.");
                    e.printStackTrace();
                }

                System.gc();

            }

        }, "Cashew-Backup-Thread");

        BACKGROUND_BACKUP_THREAD.start();

    }

    /**
     * Backs up a single cache.
     * @throws IOException Throws an IOException when the cache cannot be backed up into a file.
     */
    public static String backupMainCache() throws IOException {
        final Gson GSON = new Gson();
        StringCache<Object> mainCacheBackup = MAIN_CACHE.copy();
        final String filename = "main-cache-" + DATE_FILE_FORMAT.format(new Date(System.currentTimeMillis())) + ".cwb";

        JsonElement mainCacheBackupJsonElement = GSON.toJsonTree(mainCacheBackup);
        try (Writer writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(mainCacheBackupJsonElement, writer);
        }
        return filename;
    }

    /**
     * Backs up a cache.
     * @throws IOException Throws an IOException when the cache cannot be backed up into a file.
     */
    public static <V> String backupCache(StringCache<V> cache, String cacheName) throws IOException {
        final Gson GSON = new Gson();
        StringCache<V> cacheBackup = cache.copy();
        final String filename = cacheName + "-" + DATE_FILE_FORMAT.format(new Date(System.currentTimeMillis())) + ".cwb";
        JsonElement cacheBackupElement = GSON.toJsonTree(cacheBackup);
        try (Writer writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(cacheBackupElement, writer);
        }
        return filename;
    }

    /**
     * Sets the backup task interval (must be >=30 seconds).
     * @param backupTaskInterval The backup task interval, in milliseconds. Must be >=30000 milliseconds (30 seconds).
     */
    public static void setBackupTaskInterval(long backupTaskInterval) {
        if (backupTaskInterval >= 30000) {
            Cashew.backupTaskInterval = backupTaskInterval;
        }
    }

    /**
     * Registers a class for use in the object-oriented cache.
     * @param aClass The class to register.
     * @param timeToLive The class cache TTL.
     * @param cleanupInterval The class cache cleanup interval.
     */
    public static void registerClass(Class<?> aClass, long timeToLive, long cleanupInterval) {
        if (!OBJECT_CACHES.containsKey(aClass)) {
            StringCache<?> cache = new StringCache<>(timeToLive, cleanupInterval);
            OBJECT_CACHES.put(aClass, cache);
        }
    }

    /**
     * Registers a class for use in the object-oriented cache.
     * @param aClass The class to register.
     */
    public static void registerClass(Class<?> aClass) {
        registerClass(aClass, Cache.DEFAULT_TIME_TO_LIVE, Cache.DEFAULT_CLEANUP_INTERVAL);
    }

    /**
     * Creates a generic cache.
     * @param cacheName The cache name.
     * @param timeToLive The cache TTL.
     * @param cleanupInterval The cleanup interval.
     */
    public static void createCache(String cacheName, long timeToLive, long cleanupInterval) {
        StringCache<?> cache = new StringCache<>(timeToLive, cleanupInterval);
        GENERIC_CACHES.put(cacheName, cache);
    }

    /**
     * Creates a generic cache.
     * @param cacheName The cache name.
     */
    public static void createCache(String cacheName) {
        createCache(cacheName, Cache.DEFAULT_TIME_TO_LIVE, Cache.DEFAULT_CLEANUP_INTERVAL);
    }

    /**
     * Retrieves an instance of the main cache.
     * @return Returns the main cache.
     */
    public static StringCache<Object> getMainCache() {
        return MAIN_CACHE;
    }

    /**
     * Retrieves a class cache.
     * @param aClass The class that the cache belongs to.
     * @param <T> A type matching the class of the cache.
     * @return Returns a StringCache of type T.
     * @throws NoCacheException Throws an exception when no such cache is registered.
     */
    public static <T> StringCache<T> getCache(Class<?> aClass) throws NoCacheException {
        final StringCache<T> cache = (StringCache<T>) OBJECT_CACHES.get(aClass);
        if (cache != null) {
            return cache;
        } else {
            throw new NoCacheException(aClass);
        }
    }

    /**
     * Retrieves a generic cache.
     * @param cacheName The cache name.
     * @return Returns a StringCache.
     * @throws NoCacheException Throws an exception when the cache was not found.
     */
    public static StringCache<String> getCache(String cacheName) throws NoCacheException {
        final StringCache<String> cache = (StringCache<String>) GENERIC_CACHES.get(cacheName);
        if (cache != null) {
            return cache;
        } else {
            throw new NoCacheException(cacheName);
        }
    }

}
