package cz.muni.fi.pa165.yellowlibrary.sampledata;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;
/**
 * @author Jozef Zivcic
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class SampleDataConfiguration {

  final static Logger log = Logger.getLogger(SampleDataConfiguration.class);

  @Inject
  private SampleDataLoadingFacade loadingFacade;

  @PostConstruct
  public void dataLoading() throws IOException {
    log.debug("sample data loading");
    loadingFacade.loadData();
    log.debug("sample data loaded");
  }
}
