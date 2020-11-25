package gov.gsa.give.ipp.idemia;

import gov.gsa.give.ipp.idemia.function.EnrollmentFunction;
import gov.gsa.give.ipp.idemia.function.LocationFunction;
import gov.gsa.give.ipp.idemia.function.StatusFunction;
import gov.gsa.give.ipp.idemia.function.UpdateFunction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IdemiaPreEnrollmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdemiaPreEnrollmentApplication.class, args);
	}

	@Bean
	public EnrollmentFunction getEnrollmentFunction() {
		return new EnrollmentFunction();
	}

	@Bean
	public StatusFunction getStatusFunction() {
		return new StatusFunction();
	}

	@Bean
	public LocationFunction getLocationFunction() {
		return new LocationFunction();
	}

	@Bean
	public UpdateFunction getUpdateFunction() {
		return new UpdateFunction();
	}

}
