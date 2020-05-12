package org.nonari.fic.ri.utils.page_rank;

import java.util.List;

class Command {

    private final String comm;
    private final List<Object> args;

    Command(String comm, List<Object> args){
        this.comm = comm;
        this.args = args;
    }

    public String getComm() {
        return comm;
    }

    public List<Object> getArgs(){
        return args;
    }
}

