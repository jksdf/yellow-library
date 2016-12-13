package cz.muni.fi.pa165.yellowlibrary.sampledata;

import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.io.IOException;
/**
 * @author Jozef Zivcic
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class SampleDataConfiguration {

  final static Logger log = LoggerFactory.getLogger(SampleDataConfiguration.class);

  @Inject
  private SampleDataLoadingFacade loadingFacade;

  @PostConstruct
  public void dataLoading() throws IOException {
    log.debug("dataLoading()");
    loadingFacade.loadData();
  }
}
