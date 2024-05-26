package org.client;

import org.common.Bridge;
import org.common.Response;
import org.consoleInterface.ConsolePresenter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// this is a thread that receives clientHandlers bridges
public class Listener implements Runnable {
    private Socket socket;
    private ObjectInputStream input = null;
    private ObjectOutputStream output = null;
    private String jwToken;

    public Listener(Socket socket, ObjectInputStream input, ObjectOutputStream output) throws IOException {
        this.socket = socket;
        this.input = input;
        this.output = output;
        this.jwToken = null;
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                Bridge b = (Bridge) input.readObject();
                switch (b.getCommand()) {
                    case SIGN_UP:
                        if (b.getResponse() == Response.SUCCESSFUL_SIGNUP){
//                            this.jwToken = b.getJwToken(); // it sets this thread's token with bridge
                            ConsolePresenter.showHome(socket, output, null);
                        } else {
                            //ConsoleUtil.printErrorMSg(model);
                            //ConsoleImpl.openAccountMenu(socket, writer,jwToken);

                            ConsolePresenter.showErrorPage(socket, output, null);
                        }
                        break;
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
