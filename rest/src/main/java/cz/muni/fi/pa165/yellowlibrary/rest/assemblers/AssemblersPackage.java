package cz.muni.fi.pa165.yellowlibrary.rest.assemblers;

/**
 * This no-op marker class ony exists to provide package path for Spring dependency injection.
 *
 * @see org.springframework.context.annotation.ComponentScan#basePackageClasses
 */
public final class AssemblersPackage {
  private AssemblersPackage() {
    throw new AssertionError("This is not the class you are looking for.");
  }
}
