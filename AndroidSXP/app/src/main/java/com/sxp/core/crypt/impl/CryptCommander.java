package com.sxp.core.crypt.impl;


/*import javax.ws.rs.GET; // REST-related dependencies
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;*/

import com.sxp.core.crypt.api.hashs.Hasher; // module to test dependencies
import com.sxp.core.crypt.factories.HasherFactory;
import com.sxp.core.rest.api.ServletPath;
import com.sxp.core.rest.factories.RestServerFactory;

@ServletPath("/command/hash/*") // url path. PREFIX WITH COMMAND/ !!!
//@Path("/")
public class CryptCommander {
    //@GET
    //@Path("/{input}") // a way to name the pieces of the query
    //public String hash(@PathParam("input") String input) { // this argument will be initialized with
    public String hash( String input) { // this argument will be initialized with
        // the piece of the query
        Hasher hasher = HasherFactory.createDefaultHasher();
        return new String(hasher.getHash(input.getBytes()));
    }

    public static void main(String[] args) {
        RestServerFactory.createAndStartRestServer("jetty", 8080, "crypt");
    }
}

