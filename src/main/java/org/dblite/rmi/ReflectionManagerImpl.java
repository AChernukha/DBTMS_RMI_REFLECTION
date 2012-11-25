package org.dblite.rmi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ReflectionManagerImpl extends UnicastRemoteObject implements ReflectionManager {
    private Object obj;
    private Class cl;
    private Method method;

    public ReflectionManagerImpl() throws RemoteException {
        super();
    }

    public void setClassDetails(String className) throws RemoteException {
        try {
            cl = Class.forName(className);
            obj = cl.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Wrong details for class");
        }
    }

    public void setMethodDetails(String methodName, Class<?>... parameterTypes) throws RemoteException {
        try {
            method = cl.getMethod(methodName, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Wrong details for method");
        }
    }

    public Object invokeMethod(Object... args) throws RemoteException  {
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Can not invoke method");
        }
    }
}