package novle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoFieldType {
	public String name() default "String";
	public String pattern() default "yyyy-MM-dd";
	public String tableName() default "";
}
