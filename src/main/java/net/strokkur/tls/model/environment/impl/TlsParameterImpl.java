package net.strokkur.tls.model.environment.impl;

import net.strokkur.tls.model.environment.TlsParameter;
import net.strokkur.tls.model.type.TlsType;

public record TlsParameterImpl(
  String name,
  TlsType type
) implements TlsParameter {
}
