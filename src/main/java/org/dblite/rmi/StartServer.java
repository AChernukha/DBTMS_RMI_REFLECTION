package org.dblite.rmi;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class StartServer {

    public static void main(String[] args) throws IOException {
        LocateRegistry.createRegistry(1099);
        Naming.rebind("//127.0.0.1:1099/dblite", new ReflectionManagerImpl());
        System.out.println("Server ready");
    }

}

