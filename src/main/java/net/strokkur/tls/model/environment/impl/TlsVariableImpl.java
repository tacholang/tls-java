package net.strokkur.tls.model.environment.impl;

import net.strokkur.tls.model.environment.TlsVariable;
import net.strokkur.tls.model.type.TlsType;
import net.strokkur.tls.model.valued.TlsValue;

public record TlsVariableImpl(
  String name,
  TlsType type,
  TlsValue value
) implements TlsVariable {
}
