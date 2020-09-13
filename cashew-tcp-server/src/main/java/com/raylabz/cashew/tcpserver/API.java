package com.raylabz.cashew.tcpserver;

import com.raylabz.cashew.Cashew;
import com.raylabz.cashew.StringCache;
import com.raylabz.responz.Response;
import com.raylabz.servexpresso.InputParams;
import com.raylabz.servexpresso.ParamType;
import com.raylabz.servexpresso.Service;
import com.raylabz.servexpresso.Serviceable;

public class API {

    public static final Service ADD_SERVICE = new Service.Builder()
            .expectParam("key", ParamType.STRING, true)
            .expectParam("value", ParamType.STRING, true)
            .expectParam("class", ParamType.STRING, false)
            .implement(new Serviceable() {
                @Override
                public Response serve(InputParams params) {

                    final String key = params.getString("key");
                    final String value = params.getString("value");
                    String objectClass = null;
                    if (params.paramExists("class")) {
                        objectClass = params.getString("class");
                    }

                    StringCache<?> cache;

                    if (objectClass == null) {
                        cache = Cashew.getMainCache();
                    }
                    else {
                        
                    }

                }
            })
            .build();

}
