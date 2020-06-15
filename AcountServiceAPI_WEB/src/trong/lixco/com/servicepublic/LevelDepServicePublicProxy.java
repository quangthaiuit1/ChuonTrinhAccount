package trong.lixco.com.servicepublic;

public class LevelDepServicePublicProxy implements trong.lixco.com.servicepublic.LevelDepServicePublic {
  private String _endpoint = null;
  private trong.lixco.com.servicepublic.LevelDepServicePublic levelDepServicePublic = null;
  
  public LevelDepServicePublicProxy() {
    _initLevelDepServicePublicProxy();
  }
  
  public LevelDepServicePublicProxy(String endpoint) {
    _endpoint = endpoint;
    _initLevelDepServicePublicProxy();
  }
  
  private void _initLevelDepServicePublicProxy() {
    try {
      levelDepServicePublic = (new trong.lixco.com.servicepublic.LevelDepServicePublicServiceLocator()).getLevelDepServicePublicPort();
      if (levelDepServicePublic != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)levelDepServicePublic)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)levelDepServicePublic)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (levelDepServicePublic != null)
      ((javax.xml.rpc.Stub)levelDepServicePublic)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public trong.lixco.com.servicepublic.LevelDepServicePublic getLevelDepServicePublic() {
    if (levelDepServicePublic == null)
      _initLevelDepServicePublicProxy();
    return levelDepServicePublic;
  }
  
  public trong.lixco.com.servicepublic.LevelDep[] findAll() throws java.rmi.RemoteException{
    if (levelDepServicePublic == null)
      _initLevelDepServicePublicProxy();
    return levelDepServicePublic.findAll();
  }
  
  
}