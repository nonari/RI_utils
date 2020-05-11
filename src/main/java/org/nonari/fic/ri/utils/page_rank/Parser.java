package org.nonari.fic.ri.utils.page_rank;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

class Parser {

    static Command getCommand(String line){
        if (line.matches("add\\s+[0-9]+\\s*->\\s*[0-9]+")){
            String[] d = line.substring(3).replaceAll("\\s+","").split("->");
            List<Object> args = new ArrayList<>();
            for(String arg: d){
                if (!arg.equals("")) {
                    final int num = Integer.parseInt(arg);
                    args.add(num);
                }
            }
            return new Command("add", args);
        }

        if (line.matches("teleport\\s+[0-9]\\.[0-9]+")){
            String[] d = StringUtils.normalizeSpace(line).substring(8).split(" ");
            List<Object> args = new ArrayList<>();
            final float num = Float.parseFloat(d[1]);
            args.add(num);

            return new Command("teleport", args);
        }

        if (line.matches("iterations\\s+[0-9]+")){
            String[] d = StringUtils.normalizeSpace(line).substring(8).split(" ");
            List<Object> args = new ArrayList<>();
            final int num = Integer.parseInt(d[1]);
            args.add(num);

            return new Command("iterations", args);
        }

        if (line.matches("calc\\s*")){
            List<Object> args = new ArrayList<>();

            return new Command("calc", args);
        }

        return null;
    }

}
