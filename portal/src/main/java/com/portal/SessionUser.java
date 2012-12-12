package com.portal;

public class SessionUser implements java.io.Serializable {

    private static final long serialVersionUID = 6034793247940424913L;

    // do not make this fields nonfinal. This object is in session as serialized stream and
    // setting the fields does not refresh the object in session. You must replace it
    
    private final String userName;
    

    public SessionUser(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
	
    @Override
    public int hashCode() {
        // recipe taken from Effective Java, 2nd edition (ISBN 978-0-321-35668-0), page 47
        int result = 17;
        result = 31 * result + (null == getUserName() ? 0 : getUserName().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SessionUser)) {
            return false;
        }
        if (((SessionUser) obj).getUserName().equalsIgnoreCase(this.userName)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "SessionUser [userName=" + this.userName + "]";
    }

}
