package serialcommunication.Daisy;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;

public class SerialReader implements SerialPortEventListener
    {
        private InputStream in;
        private byte[] buffer = new byte[1024];
        private GetAnswer getAnswer;
        public SerialReader (
                InputStream in, GetAnswer getAnswer )
        {
            this.in = in;
            this.getAnswer = getAnswer;
        }

        @Override
        public void serialEvent(SerialPortEvent arg0) {
            int data;

            try
            {
                int len = 0;
                while ( ( data = in.read()) > -1 )
                {
                    if ( data == '\n' ) {
                        break;
                    }
                    buffer[len++] = (byte) data;
                }
                String answer = new String(buffer,0,len);
                System.out.print("device response " + answer);
                getAnswer.showAnswer(answer);
            }
            catch ( IOException e )
            {
                e.printStackTrace();
                System.exit(-1);
            }
        }

}
