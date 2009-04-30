package com.family168.security.acl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Iterator;

import org.aopalliance.intercept.MethodInvocation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import org.springframework.security.Authentication;
import org.springframework.security.AuthorizationServiceException;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.acls.Acl;
import org.springframework.security.acls.AclService;
import org.springframework.security.acls.NotFoundException;
import org.springframework.security.acls.Permission;
import org.springframework.security.acls.objectidentity.ObjectIdentity;
import org.springframework.security.acls.objectidentity.ObjectIdentityRetrievalStrategy;
import org.springframework.security.acls.objectidentity.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.sid.Sid;
import org.springframework.security.acls.sid.SidRetrievalStrategy;
import org.springframework.security.acls.sid.SidRetrievalStrategyImpl;
import org.springframework.security.vote.AccessDecisionVoter;
import org.springframework.security.vote.AclEntryVoter;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;


/**
 * acl投票器.
 *
 *
 */
public class AclVoter extends AclEntryVoter {
    /** * logger. */
    private static Log logger = LogFactory.getLog(AclEntryVoter.class);

    /** * AclService. */
    private AclService aclService;

    /** * 对象身份检索策略. */
    private ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy =
        new ObjectIdentityRetrievalStrategyImpl();

    /** * sid检索策略. */
    private SidRetrievalStrategy sidRetrievalStrategy = new SidRetrievalStrategyImpl();

    /** * 内部方法. */
    private String internalMethod;

    /** * 处理配置属性. */
    private String processConfigAttribute;

    /** * 必须的权限. */
    private Permission[] requirePermission;

    /**
     * 构造方法.
     *
     * @param aclService AclService
     * @param processConfigAttribute String
     * @param requirePermission Permission[]
     */
    public AclVoter(AclService aclService, String processConfigAttribute,
        Permission[] requirePermission) {
        super(aclService, processConfigAttribute, requirePermission);
        Assert.notNull(processConfigAttribute,
            "A processConfigAttribute is mandatory");
        Assert.notNull(aclService, "An AclService is mandatory");

        if ((requirePermission == null) || (requirePermission.length == 0)) {
            throw new IllegalArgumentException(
                "One or more requirePermission entries is mandatory");
        }

        this.aclService = aclService;
        this.processConfigAttribute = processConfigAttribute;

        this.requirePermission = new Permission[requirePermission.length];
        System.arraycopy(requirePermission, 0, this.requirePermission, 0,
            requirePermission.length);
    }

    /**
     * Optionally specifies a method of the domain object that will be used to obtain a contained domain
     * object. That contained domain object will be used for the ACL evaluation. This is useful if a domain object
     * contains a parent that an ACL evaluation should be targeted for, instead of the child domain object (which
     * perhaps is being created and as such does not yet have any ACL permissions)
     *
     * @return <code>null</code> to use the domain object, or the name of a method (that requires no arguments) that
     *         should be invoked to obtain an <code>Object</code> which will be the domain object used for ACL
     *         evaluation
     */
    protected String getInternalMethod() {
        return internalMethod;
    }

    /**
     * 设置内部方法.
     *
     * @param internalMethod 内部方法
     */
    public void setInternalMethod(String internalMethod) {
        this.internalMethod = internalMethod;
    }

    /**
     * 获得处理配置属性.
     *
     * @return 字符串
     */
    protected String getProcessConfigAttribute() {
        return processConfigAttribute;
    }

    /**
     * 设置对象身份检索策略.
     *
     * @param objectIdentityRetrievalStrategy ObjectIdentityRetrievalStrategy
     */
    public void setObjectIdentityRetrievalStrategy(
        ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy) {
        Assert.notNull(objectIdentityRetrievalStrategy,
            "ObjectIdentityRetrievalStrategy required");
        this.objectIdentityRetrievalStrategy = objectIdentityRetrievalStrategy;
    }

    /**
     * 设置检索sid的策略.
     *
     * @param sidRetrievalStrategy SidRetrievalStrategy
     */
    public void setSidRetrievalStrategy(
        SidRetrievalStrategy sidRetrievalStrategy) {
        Assert.notNull(sidRetrievalStrategy,
            "SidRetrievalStrategy required");
        this.sidRetrievalStrategy = sidRetrievalStrategy;
    }

    /**
     * 是否支持某一配置属性.
     *
     * @param attribute 配置属性
     * @return 是否
     */
    public boolean supports(ConfigAttribute attribute) {
        //logger.info(attribute.getAttribute());
        //logger.info(getProcessConfigAttribute());
        return (attribute.getAttribute() != null)
        && attribute.getAttribute().equals(getProcessConfigAttribute());
    }

    /**
     * 投票.
     *
     * @param authentication 验证
     * @param object 对象
     * @param config 配置属性
     * @return 通过，拒绝，弃权
     */
    public int vote(Authentication authentication, Object object,
        ConfigAttributeDefinition config) {
        logger.info(authentication);
        logger.info(object);
        logger.info(config);

        Iterator iter = config.getConfigAttributes().iterator();

        while (iter.hasNext()) {
            ConfigAttribute attr = (ConfigAttribute) iter.next();

            //logger.info(attr);
            //logger.info(this.supports(attr));
            if (!this.supports(attr)) {
                continue;
            }

            logger.info("not continue");
            logger.info(object);

            try {
                logger.info(getDomainObjectInstance(object));
            } catch (Exception ex) {
                logger.error(ex, ex);
            }

            // Need to make an access decision on this invocation
            // Attempt to locate the domain object instance to process
            Object domainObject = getDomainObjectInstance(object);
            logger.info(domainObject);

            // If domain object is null, vote to abstain
            if (domainObject == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug(
                        "Voting to abstain - domainObject is null");
                }

                return AccessDecisionVoter.ACCESS_ABSTAIN;
            }

            logger.info("after check domain null");

            // Evaluate if we are required to use an inner domain object
            if (StringUtils.hasText(internalMethod)) {
                try {
                    Class clazz = domainObject.getClass();
                    Method method = clazz.getMethod(internalMethod,
                            new Class[0]);
                    domainObject = method.invoke(domainObject,
                            new Object[0]);
                } catch (NoSuchMethodException nsme) {
                    throw new AuthorizationServiceException(
                        "Object of class '" + domainObject.getClass()
                        + "' does not provide the requested internalMethod: "
                        + internalMethod);
                } catch (IllegalAccessException iae) {
                    logger.debug("IllegalAccessException", iae);

                    throw new AuthorizationServiceException(
                        "Problem invoking internalMethod: "
                        + internalMethod + " for object: " + domainObject);
                } catch (InvocationTargetException ite) {
                    logger.debug("InvocationTargetException", ite);

                    throw new AuthorizationServiceException(
                        "Problem invoking internalMethod: "
                        + internalMethod + " for object: " + domainObject);
                }
            }

            // Obtain the OID applicable to the domain object
            ObjectIdentity objectIdentity = objectIdentityRetrievalStrategy
                .getObjectIdentity(domainObject);
            logger.info(objectIdentity);

            // Obtain the SIDs applicable to the principal
            Sid[] sids = sidRetrievalStrategy.getSids(authentication);
            logger.info(sids);

            Acl acl;

            try {
                // Lookup only ACLs for SIDs we're interested in
                acl = aclService.readAclById(objectIdentity, sids);
            } catch (NotFoundException nfe) {
                logger.error(nfe);

                if (logger.isDebugEnabled()) {
                    logger.debug(
                        "Voting to deny access - no ACLs apply for this principal");
                }

                logger.info("ACCESS_DENIED");

                return AccessDecisionVoter.ACCESS_DENIED;
            }

            logger.info(acl);
            logger.info(acl.isGranted(requirePermission, sids, false));

            try {
                if (acl.isGranted(requirePermission, sids, false)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Voting to grant access");
                    }

                    return AccessDecisionVoter.ACCESS_GRANTED;
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug(
                            "Voting to deny access - ACLs returned, but insufficient permissions for this principal");
                    }

                    logger.info("ACCESS_DENIED");

                    return AccessDecisionVoter.ACCESS_DENIED;
                }
            } catch (NotFoundException nfe) {
                if (logger.isDebugEnabled()) {
                    logger.debug(
                        "Voting to deny access - no ACLs apply for this principal");
                }

                logger.info("ACCESS_DENIED");

                return AccessDecisionVoter.ACCESS_DENIED;
            }
        }

        // No configuration attribute matched, so abstain
        return AccessDecisionVoter.ACCESS_ABSTAIN;
    }

    /**
     * 获得领域对象实例.
     *
     * @param secureObject 安全对象
     * @return 实例
     */
    protected Object getDomainObjectInstance(Object secureObject) {
        Object[] args;
        Class[] params;

        if (secureObject instanceof MethodInvocation) {
            MethodInvocation invocation = (MethodInvocation) secureObject;
            params = invocation.getMethod().getParameterTypes();
            args = invocation.getArguments();
        } else {
            JoinPoint jp = (JoinPoint) secureObject;
            params = ((CodeSignature) jp.getStaticPart().getSignature())
                .getParameterTypes();
            args = jp.getArgs();
        }

        logger.info(getProcessDomainObjectClass());

        for (int i = 0; i < params.length; i++) {
            logger.info(params[i]);
            logger.info(args[i]);

            try {
                if (getProcessDomainObjectClass()
                            .isAssignableFrom(params[i])) {
                    return args[i];
                }

                return getProcessDomainObjectClass().cast(args[i]);
            } catch (Exception ex) {
                logger.info(ex);
            }
        }

        throw new AuthorizationServiceException("Secure object: "
            + secureObject + " did not provide any argument of type: "
            + getProcessDomainObjectClass());
    }
}
