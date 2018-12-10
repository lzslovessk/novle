package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import novle.annotation.DtoFieldType;
import novle.model.SearchCommonDto;

public class DataGridFilter4MybatisUtil {
	public static final Logger logger = LoggerFactory.getLogger("DataGridFilter4MybatisUtil");
	/**
	 * 将条件查询的参数放入SearchCommonDto,以便组装sql语句使用(在调用Dao之前使用)
	 * @param t 实体类对象
	 * @param request 当前request对象
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends SearchCommonDto> void seperateRequest(T t, HttpServletRequest request){
		Class tClass = t.getClass();
		Field[] fields = tClass.getDeclaredFields();
		//获取request内参数列表
		Map<String,String[]> map = request.getParameterMap();
//		//获取参数列表所有名称
//		Set<String> set = map.keySet();
//		List<String> list = new ArrayList<String>(set);
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			if(map.get(fieldName+".operator") != null && map.get(fieldName+".operator").length > 0){
				String[] vals=map.get(fieldName+".operator");
				//将"="、"!="、"like"操作的值放入SearchCommonDto
				if(t.getList() != null && t.getList().length >0){
					String[] _vals = t.getList();
					List<String> StringList = new ArrayList<String>();
					for (String val : _vals) {//拿到之前过滤条件的值
						StringList.add(val);
					}
					for (String val : vals) {//拿到本次过滤条件的值
						StringList.add(val);
					}
					t.setList(StringList.toArray(new String[StringList.size()]));
				}else{
					t.setList(vals);
				}
			}
			if(map.get(fieldName+".andor") != null && map.get(fieldName+".andor").length > 0){
				String[] andors = map.get(fieldName+".andor");
				if(t.getAndOrList() != null && t.getAndOrList().length > 0){
					String[] _andors = t.getAndOrList();
					List<String> StringList = new ArrayList<String>();
					for (String _andor : _andors) {//拿到之前andor的值
						StringList.add(_andor);
					}
					for (String andor : andors) {//拿到本次andor的值
						StringList.add(andor);
					}
					t.setAndOrList(StringList.toArray(new String[StringList.size()]));
				}else{
					t.setAndOrList(andors);
				}
			}
		}
		//判断是否有排序字段----orderField为排序字段名，orderDirection为排序值
		if(map.get("orderField") != null && map.get("orderField").length > 0){//只有单列的排序
			t.setOrderField(map.get("orderField")[0]);
			t.setOrderDirection(map.get("orderDirection")[0]);
		}
	}
	
	/**
	 * 组装查询语句(要求前台页面的表头属性要按照实体类的顺序来排序),要求:①原始sql语句是以" where 1=1 "结尾②现版本只限于单表查询
	 * @param t 实体类对象
	 * @param tableName 表名(如有别名传别名,没有别名传表名)
	 * @param countSql 查询总条数sql
	 * @param querySql 查询结果sql
	 * @param paramList 成员变量的值的list
	 */
	@SuppressWarnings({"rawtypes", "unchecked", "unused"})
	public static <T extends SearchCommonDto> void handleListSql(T t, String tableName, StringBuffer countSql, StringBuffer querySql, Map paramMap){
		//用于取第(length-_n)个"="、"!="、"like",_n初始值为length,每用一个-1
		int _n = 0;
		if(t.getList()!=null&&t.getList().length>0){
			_n = t.getList().length;
		}
		//获取传入对象t的类型T
		Class tClass = t.getClass();
		//获取T的成员变量
		Field[] fields = tClass.getDeclaredFields();
		int k = 0;//取andor的下标
		int paramIndex = 0;
		for (int i = 0 ; i < fields.length ; i ++) {
//			try {
//				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), tClass);
//				//获取成员变量的set方法
//				Method _set = pd.getWriteMethod();
//				//获取成员变量的get方法
//				Method _get = pd.getReadMethod();
				//判断是否是注解的成员变量(继承类内含有没有注解的成员变量)
				if(fields[i].isAnnotationPresent(DtoFieldType.class)){
					//获取成员变量的名字
					String fieldName = fields[i].getName();
					//将成员变量属性由private变为可以访问
					fields[i].setAccessible(true);
					try {
						//获取成员变量的值
						Object[] fieldValue = null;
						if(i == 0 && (fields[i].getType() == Long.class || fields[i].getType() == long.class)){
							
						}else{
							fieldValue = (Object[]) fields[i].get(t);
//						if(_fieldValue.getClass().isArray()){
//							
//						}
						}
						//判断成员变量是否有值
						if(fieldValue != null){
//							boolean flg = true;
//							//如果是String数组类型,判断其是否是多条件查询
//							if("String".equals(fields[i].getAnnotation(DtoFieldType.class).name())){
//								
//							}else if("Double".equals(fields[i].getAnnotation(DtoFieldType.class).name())){
//								
//							}else if("Integer".equals(fields[i].getAnnotation(DtoFieldType.class).name())){
//								
//							}else{
//								flg=false;
//							}
//							if(flg){
								countSql.append(" and ( ");
								querySql.append(" and ( ");
 								if(fieldValue.length>1){
									for (int j = 0 ; j < fieldValue.length ; j ++) {
										Object value = fieldValue[j];
										if(StringUtil.isNotNull(value)){
											boolean _concatOrNot = false;//是否组装"and 1=2"SQL语句在末尾
											if("Double".equals(fields[i].getAnnotation(DtoFieldType.class).name())){
												try {
													double _value = Double.valueOf(fieldValue[j].toString());
												} catch (Exception e) {
													_concatOrNot = true;
												}
											}else if("Integer".equals(fields[i].getAnnotation(DtoFieldType.class).name())){
												try {
													Integer _value = Integer.valueOf(fieldValue[j].toString());
												} catch (Exception e) {
													_concatOrNot = true;
												}
											}
											//如果是多个查询条件,第一个查询条件不加"and"或者"or",以后有"and"或者"or"的查询
											if(j==0){
//											if(t.getAndOrList()!=null){
												//有andor
												if(_concatOrNot){//拼装" 1=2"SQL语句,标识输入任何错误类型的查询条件
													countSql.append(" 1 = 2 ");
													querySql.append(" 1 = 2 ");
												}else{
													countSql.append(getTableName(fields[i], tableName) + "." + fieldName + " " + t.getList()[t.getList().length-_n] + " #{param" + paramIndex + "} ");
													querySql.append(getTableName(fields[i], tableName) + "." + fieldName + " " + t.getList()[t.getList().length-_n] + " #{param" + paramIndex + "} ");
												}
//											}
											}else{
												if(t.getAndOrList()!=null){
													//有andor
													if(_concatOrNot){//拼装" 1=2"SQL语句,标识输入任何错误类型的查询条件
														countSql.append(t.getAndOrList()[k]+" 1 = 2 ");
														querySql.append(t.getAndOrList()[k]+" 1 = 2 ");
													}else{
														countSql.append(t.getAndOrList()[k] + " " + getTableName(fields[i], tableName) + "." + fieldName + " " + t.getList()[t.getList().length-_n] + " #{param" + paramIndex + "} ");
														querySql.append(t.getAndOrList()[k] + " " + getTableName(fields[i], tableName) + "." + fieldName + " " + t.getList()[t.getList().length-_n] + " #{param" + paramIndex + "} ");
													}
												}
												k++;
											}
											if("like".equals(t.getList()[t.getList().length-_n])){
												if(!_concatOrNot){
													paramMap.put("param" + paramIndex, "%" + value + "%");
													paramIndex++;
												}
											}else{
												if(!_concatOrNot){
													paramMap.put("param" + paramIndex, buildValue(fields[i], value));
													paramIndex++;
												}
											}
											_n--;//每次条件查询都将_n减1,用于下一次取下一个值
										}else{
											if(j == 0){
												//如果前台传来两个空字符串数组
												countSql.append(" 1 = 1 ");
												querySql.append(" 1 = 1 ");
											}else{
												countSql.append(" and 1 = 1 ");
												querySql.append(" and 1 = 1 ");
											}
										}
									}
								}else{
									if("like".equals(t.getList()[t.getList().length-_n])){
										countSql.append(getTableName(fields[i], tableName) + "." + fieldName + " " + t.getList()[t.getList().length-_n] + " #{param" + paramIndex + "} ");
										querySql.append(getTableName(fields[i], tableName) + "." + fieldName + " " + t.getList()[t.getList().length-_n] + " #{param" + paramIndex + "} ");
										paramMap.put("param" + paramIndex, "%" + fieldValue[0] + "%");
										paramIndex++;
									}else{
										Object value = buildValue(fields[i], fieldValue[0]);
										if (value == null) {
											countSql.append(" 1 = 2 ");
											querySql.append(" 1 = 2 ");
										} else {
											countSql.append(getTableName(fields[i], tableName) + "." + fieldName + " " + t.getList()[t.getList().length-_n] + " #{param" + paramIndex + "} ");
											querySql.append(getTableName(fields[i], tableName) + "." + fieldName + " " + t.getList()[t.getList().length-_n] + " #{param" + paramIndex + "} ");
											paramMap.put("param" + paramIndex, value);
											paramIndex++;
										}
									}
									if(_n>0){
										_n--;
									}
								}
								countSql.append(" ) ");
								querySql.append(" ) ");
//							}
						}
						//将成员变量属性变回private
						fields[i].setAccessible(false);
					} catch (IllegalArgumentException e) {
						logger.error("异常",e);
					} catch (IllegalAccessException e) {
						logger.error("异常",e);
					}
				}
//			} catch (IntrospectionException e) {
//				logger.error("异常",e);
//			}
		}
		//如果有排序,则拼装排序
		if(StringUtil.isNotNull(t.getOrderField())){
			//程序中约定 时间格式化得字符串 时间成员变量    结尾都为Format
			String filed = t.getOrderField();
			if(filed.endsWith("Format")){
				filed = filed.substring(0, filed.lastIndexOf("Format"));
			}
			countSql.append(" order by " + filed + " " + t.getOrderDirection()) ;
			querySql.append(" order by " + filed + " " + t.getOrderDirection()) ;
		}
		
	}
	
	private static String getTableName(Field field, String defaultName) {
		String tableName = defaultName;
		if (field.isAnnotationPresent(DtoFieldType.class)) {
			tableName = field.getAnnotation(DtoFieldType.class).tableName();
			if (StringUtil.isNull(tableName)) {
				tableName = defaultName;
			}
		}
		return tableName;
	}
	
	private static Object buildValue(Field field, Object fieldValue) {
		Object retObject = fieldValue;
		String columnType = field.getAnnotation(DtoFieldType.class).name();
		if ("Double".equalsIgnoreCase(columnType)) {
			try {
				retObject = Double.valueOf(fieldValue.toString());
			} catch (Exception e) {
				retObject = null;
			}
		} else if ("Integer".equalsIgnoreCase(columnType)) {
			try {
				retObject = Integer.valueOf(fieldValue.toString());
			} catch (Exception e) {
				retObject = null;
			}
		} else if ("Date".equalsIgnoreCase(columnType)) {
			retObject = DateUtils.parseDate(fieldValue.toString(), field.getAnnotation(DtoFieldType.class).pattern());
		}
		return retObject;
	}
}
