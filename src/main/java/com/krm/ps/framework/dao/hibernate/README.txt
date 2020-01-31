<!-- 以下配置用于处理Blob字段 -->
<bean id="oracleLobHandler" class="com.krm.dao.hibernate.support.lob.OracleLobHandler"/>
    
    <!-- Default DAO - can be used when doing standard CRUD -->
    <bean id="defaultDAO" class="com.krm.dao.hibernate.DefaultDAOHibernate">
        <property name="sessionFactory" ref="sessionFactory"/>
        <property name="lobHandler" ref="oracleLobHandler"/>
    </bean>