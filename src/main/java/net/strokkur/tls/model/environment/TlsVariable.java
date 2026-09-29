package net.strokkur.tls.model.environment;

import net.strokkur.tls.model.environment.impl.TlsVariableImpl;
import net.strokkur.tls.model.type.TlsType;
import net.strokkur.tls.model.valued.TlsValue;

public interface TlsVariable {

  static TlsVariable variable(String name, TlsType type, TlsValue value) {
    return new TlsVariableImpl(name, type, value);
  }

  String name();

  TlsType type();

  TlsValue value();
}
