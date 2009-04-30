/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.captcha;

import org.springframework.security.context.SecurityContextImpl;


/**
 * Default CaptchaSecurityContext implementation.
 *
 * @author Marc-Antoine Garrigue
 * @version $Id: CaptchaSecurityContextImpl.java 2743 2008-03-17 11:06:32Z luke_t $
 */
public class CaptchaSecurityContextImpl extends SecurityContextImpl
    implements CaptchaSecurityContext {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** HASH_CODE. */
    public static final int HASH_CODE = -37;

    /** * human. */
    private boolean human;

    /** * human restricted rsources requests count. */
    private int humanRestrictedResourcesRequestsCount;

    /** * last passed captcha date. */
    private long lastPassedCaptchaDate;

    /** * constructor. */
    public CaptchaSecurityContextImpl() {
        human = false;
        lastPassedCaptchaDate = 0;
        humanRestrictedResourcesRequestsCount = 0;
    }

    /**
     * @param obj Object.
     * @return boolean
     */
    public boolean equals(Object obj) {
        if (obj instanceof CaptchaSecurityContextImpl) {
            CaptchaSecurityContextImpl rhs = (CaptchaSecurityContextImpl) obj;

            if (this.isHuman() != rhs.isHuman()) {
                return false;
            }

            if (this.getHumanRestrictedResourcesRequestsCount() != rhs
                    .getHumanRestrictedResourcesRequestsCount()) {
                return false;
            }

            if (this.getLastPassedCaptchaDateInMillis() != rhs
                    .getLastPassedCaptchaDateInMillis()) {
                return false;
            }

            return super.equals(obj);
        }

        return false;
    }

    /** * @return humanRestrictedResourcesRequestsCount. */
    public int getHumanRestrictedResourcesRequestsCount() {
        return humanRestrictedResourcesRequestsCount;
    }

    /** * @return lastPassedCaptchaDate. */
    public long getLastPassedCaptchaDateInMillis() {
        return lastPassedCaptchaDate;
    }

    /** * @return hash code. */
    public int hashCode() {
        int code = super.hashCode();
        code ^= this.humanRestrictedResourcesRequestsCount;
        code ^= this.lastPassedCaptchaDate;

        if (this.isHuman()) {
            code ^= HASH_CODE;
        }

        return code;
    }

    /**
     * Method to increment the human Restricted Resources Requests Count.
     */
    public void incrementHumanRestrictedResourcesRequestsCount() {
        humanRestrictedResourcesRequestsCount++;
    }

    /** * @return human. */
    public boolean isHuman() {
        return human;
    }

    /**
     * Reset the lastPassedCaptchaDate and count.
     */
    public void setHuman() {
        this.human = true;
        this.lastPassedCaptchaDate = System.currentTimeMillis();
        this.humanRestrictedResourcesRequestsCount = 0;
    }
}
