package net.strokkur.tls.model.valued;

import java.math.BigDecimal;

public interface TlsValue {

  String token();

  long asInt() throws UnexpectedTypeException;

  long asUint() throws UnexpectedTypeException;

  BigDecimal asFloat() throws UnexpectedTypeException;

  BigDecimal asUfloat() throws UnexpectedTypeException;

  String asString() throws UnexpectedTypeException;

  char asChar() throws UnexpectedTypeException;

  boolean asBool() throws UnexpectedTypeException;
}
