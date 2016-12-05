package cz.muni.fi.pa165.yellowlibrary.service.facade;

/**
 * This no-op marker class ony exists to provide package path for Spring dependency injection.
 *
 * @see org.springframework.context.annotation.ComponentScan#basePackageClasses
 */
public final class FacadePackage {
  private FacadePackage() {
    throw new AssertionError("This is not a class you are looking for.");
  }
}
