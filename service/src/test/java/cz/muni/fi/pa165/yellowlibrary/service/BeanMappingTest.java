package cz.muni.fi.pa165.yellowlibrary.service;

import com.google.common.collect.ImmutableList;

import org.dozer.MappingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Norbert Slivka
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BeanMappingTest extends AbstractTestNGSpringContextTests {
  @Inject
  private BeanMappingService service;

  @Test
  public void enumTest() {
    assertThat(service.mapTo(TestEnum1.A, TestEnum2.class)).isEqualTo(TestEnum2.A);
  }

  @Test(expectedExceptions = MappingException.class)
  public void enumTestNoMapping() {
    service.mapTo(TestEnum1.B, TestEnum2.class);
  }

  @Test
  public void mapToNull() {
    assertThat(service.mapTo(null, Object.class)).isNull();
  }

  @Test
  public void mapEmptyCollection() {
    assertThat(service.mapTo(ImmutableList.of(), Object.class)).isEmpty();
  }

  public enum TestEnum1 {
    A, B
  }

  public enum TestEnum2 {
    A
  }
}
