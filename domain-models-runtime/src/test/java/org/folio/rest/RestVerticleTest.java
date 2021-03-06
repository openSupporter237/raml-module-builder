package org.folio.rest;

import static org.folio.rest.jaxrs.model.CalendarPeriodsServicePointIdCalculateopeningGetUnit.*;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RestVerticleTest {

  Object parseEnum(String value, String defaultValue) throws Exception {
    return RestVerticle.parseEnum(
          "org.folio.rest.jaxrs.model.CalendarPeriodsServicePointIdCalculateopeningGetUnit",
          value, defaultValue);
  }

  @Test
  void parseEnum() throws Exception {
    assertThat(parseEnum(null,           null  ), is(nullValue()));
    assertThat(parseEnum("",             ""    ), is(nullValue()));
    assertThat(parseEnum("day",          "hour"), is(DAY));
    assertThat(parseEnum("hour",         "day" ), is(HOUR));
    assertThat(parseEnum("foo",          "day" ), is(DAY));
    assertThat(parseEnum(null,           "day" ), is(DAY));
    assertThat(parseEnum("foo",          "hour"), is(HOUR));
    assertThat(parseEnum(null,           "hour"), is(HOUR));
    assertThat(parseEnum("bee interval", ""    ), is(BEEINTERVAL));
  }

  @Test
  void parseEnumUnknownType() {
    assertThrows(ClassNotFoundException.class, () -> RestVerticle.parseEnum("foo.bar", "foo", "bar"));
  }

  @Test
  void parseEnumNonEnumClass() throws Exception {
    assertThat(RestVerticle.parseEnum("java.util.Vector", "foo", "bar"), is(nullValue()));
  }

  @Test
  void javaVersion() {
    assertThat(RestVerticle.compareJavaVersion("1.2.3_4", "1.2.3_4"), is(0));
    assertThat(RestVerticle.compareJavaVersion("1.2.3_4", "1.2.3_5"), is(lessThan(0)));
    assertThat(RestVerticle.compareJavaVersion("1.2.3_4", "1.2.3_10"), is(lessThan(0)));
    assertThat(RestVerticle.compareJavaVersion("1.2.3_4", "1.2.10_3"), is(lessThan(0)));
    assertThat(RestVerticle.compareJavaVersion("1.2.3_4", "1.10.2_3"), is(lessThan(0)));
    assertThrows(InternalError.class, () -> RestVerticle.checkJavaVersion("1.8.0_99"));
    RestVerticle.checkJavaVersion("1.8.0_1000000");  // assert no exception
  }
}
