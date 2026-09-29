package net.strokkur.tls.model.environment.impl;

import net.strokkur.tls.model.environment.TlsMethod;
import net.strokkur.tls.model.environment.TlsParameter;
import net.strokkur.tls.model.type.TlsType;
import net.strokkur.tls.model.valued.TlsParameterValue;
import net.strokkur.tls.model.valued.TlsValue;

import java.util.List;
import java.util.function.BiFunction;

public record TlsMethodImpl(
  String name,
  TlsType type,
  List<TlsParameter> parameters,
  BiFunction<TlsMethod, List<TlsParameterValue>, TlsValue> valueFunction
) implements TlsMethod {

  @Override
  public TlsValue value(List<TlsParameterValue> values) {
    return valueFunction.apply(this, values);
  }
}
