package DataProviders.JBDCDataProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JDBCDataSource {

    String driverClass() default "org.h2.Driver";
    String connectionURL();
    String statementQuery();
    String user() default "";
    String password() default "";
}
