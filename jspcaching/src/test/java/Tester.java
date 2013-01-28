import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class Tester {

    class Builder {
        final StringBuilder stringBuilder;

        public Builder(String start){
            this.stringBuilder = new StringBuilder(start);
        }

        public void add(String toAdd){
            this.stringBuilder.append(toAdd);
        }

        public String flush(){
            return  this.stringBuilder.toString();
        }
    }

    @Test
    public void testHash(){

        Builder first = new Builder("mitja");
        Builder second = new Builder("nika");

        System.out.println(first.hashCode());
        System.out.println(second.hashCode());

        assertTrue(first.hashCode() == second.hashCode());



    }

}
