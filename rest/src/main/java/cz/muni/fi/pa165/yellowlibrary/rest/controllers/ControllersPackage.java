package cz.muni.fi.pa165.yellowlibrary.rest.controllers;

/**
 * This no-op marker class ony exists to provide package path for Spring dependency injection.
 *
 * @see org.springframework.context.annotation.ComponentScan#basePackageClasses
 */
public final class ControllersPackage {
  private ControllersPackage() {
    throw new AssertionError("This is not the class you are looking for.");
  }
}
