package org.nonari.aiseo.application;


import java.util.ArrayList;
import java.util.List;

class Parser {

    //Command matches "# ss -> dd = "  format
    static Command getCommand(String line){
        if (line.matches("# *[0-9][0-9]* *-> *[0-9][0-9]* *= *")){
            String[] d = line.replaceAll("\\s+","").split("#|=|->");
            List<Object> args = new ArrayList<>();
            for(String arg: d){
                if (!arg.equals("")) {
                    Integer num = new Integer(arg);
                    args.add(num);
                }
            }
            return new Command("path", args);
        }
        //Command matches "ss -> dd = ww"  format
        if (line.matches(" *[0-9][0-9]* *-> *[0-9][0-9]* *= *[0-9][0-9]*")){
            String[] d = line.replaceAll("\\s+","").split("#|=|->");
            List<Integer> args = new ArrayList<>();
            for(String arg: d){
                if (!arg.equals("")) {
                    Integer num = new Integer(arg);
                    args.add(num);
                }
            }
            return new Command("add", args);
        }

        return null;
    }

}
