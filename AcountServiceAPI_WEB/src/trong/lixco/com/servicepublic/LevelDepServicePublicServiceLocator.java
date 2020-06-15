/**
 * LevelDepServicePublicServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package trong.lixco.com.servicepublic;

import trong.lixco.com.util.StaticPath;

public class LevelDepServicePublicServiceLocator extends org.apache.axis.client.Service implements trong.lixco.com.servicepublic.LevelDepServicePublicService {

    public LevelDepServicePublicServiceLocator() {
    }


    public LevelDepServicePublicServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LevelDepServicePublicServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LevelDepServicePublicPort
    private java.lang.String LevelDepServicePublicPort_address = StaticPath.getPath()+"/dulieutrungtam_ejb/LevelDepServicePublic";

    public java.lang.String getLevelDepServicePublicPortAddress() {
        return LevelDepServicePublicPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LevelDepServicePublicPortWSDDServiceName = "LevelDepServicePublicPort";

    public java.lang.String getLevelDepServicePublicPortWSDDServiceName() {
        return LevelDepServicePublicPortWSDDServiceName;
    }

    public void setLevelDepServicePublicPortWSDDServiceName(java.lang.String name) {
        LevelDepServicePublicPortWSDDServiceName = name;
    }

    public trong.lixco.com.servicepublic.LevelDepServicePublic getLevelDepServicePublicPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LevelDepServicePublicPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLevelDepServicePublicPort(endpoint);
    }

    public trong.lixco.com.servicepublic.LevelDepServicePublic getLevelDepServicePublicPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            trong.lixco.com.servicepublic.LevelDepServicePublicServiceSoapBindingStub _stub = new trong.lixco.com.servicepublic.LevelDepServicePublicServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getLevelDepServicePublicPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLevelDepServicePublicPortEndpointAddress(java.lang.String address) {
        LevelDepServicePublicPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (trong.lixco.com.servicepublic.LevelDepServicePublic.class.isAssignableFrom(serviceEndpointInterface)) {
                trong.lixco.com.servicepublic.LevelDepServicePublicServiceSoapBindingStub _stub = new trong.lixco.com.servicepublic.LevelDepServicePublicServiceSoapBindingStub(new java.net.URL(LevelDepServicePublicPort_address), this);
                _stub.setPortName(getLevelDepServicePublicPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("LevelDepServicePublicPort".equals(inputPortName)) {
            return getLevelDepServicePublicPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://servicepublic.com.lixco.trong/", "LevelDepServicePublicService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://servicepublic.com.lixco.trong/", "LevelDepServicePublicPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LevelDepServicePublicPort".equals(portName)) {
            setLevelDepServicePublicPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
