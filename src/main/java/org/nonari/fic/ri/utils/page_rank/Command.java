package org.nonari.fic.ri.utils.page_rank;

import java.util.List;

//This class represents the command invoked by the user
class Command {

    //Command name
    private String comm;
    //Arguments list
    private List args;

    Command(String comm, List args){
        this.comm = comm;
        this.args = args;
    }

    String getComm() {
        return comm;
    }

    List getArgs(){
        return args;
    }
}

