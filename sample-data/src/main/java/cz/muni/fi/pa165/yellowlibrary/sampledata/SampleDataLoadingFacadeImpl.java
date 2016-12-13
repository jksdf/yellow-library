package cz.muni.fi.pa165.yellowlibrary.sampledata;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jozef Zivcic
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

  @Override
  public void loadData() {
    System.out.println("Hello world");
  }
}
