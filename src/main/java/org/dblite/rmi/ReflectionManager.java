package org.dblite.rmi;

import java.rmi.RemoteException;

public interface ReflectionManager extends java.rmi.Remote {

    public void setClassDetails(String className) throws RemoteException;

    public void setMethodDetails(String methodName, Class<?>... parameterTypes) throws RemoteException;

    public Object invokeMethod(Object... args) throws RemoteException;

}