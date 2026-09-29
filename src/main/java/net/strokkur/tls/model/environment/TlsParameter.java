package net.strokkur.tls.model.environment;

import net.strokkur.tls.model.environment.impl.TlsParameterImpl;
import net.strokkur.tls.model.type.TlsType;

public interface TlsParameter {

  static TlsParameter parameter(String name, TlsType type) {
    return new TlsParameterImpl(name, type);
  }

  String name();

  TlsType type();
}
