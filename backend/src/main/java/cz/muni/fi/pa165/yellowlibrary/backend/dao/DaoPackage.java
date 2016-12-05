package cz.muni.fi.pa165.yellowlibrary.backend.dao;

/**
 * This no-op marker class ony exists to provide package path for Spring dependency injection.
 *
 * @see org.springframework.context.annotation.ComponentScan#basePackageClasses
 */
public final class DaoPackage {
  private DaoPackage() {
    throw new AssertionError("This is not a class you are looking for.");
  }
}
