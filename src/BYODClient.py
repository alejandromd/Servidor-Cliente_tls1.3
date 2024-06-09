from tkinter import simpledialog, messagebox
import socket
import ssl

class BYODClient:
    def __init__(self):

        protocols = ['TLSv1.3']

        # Create an SSL/TLS context and set the options
        context = ssl.SSLContext(ssl.PROTOCOL_TLS_CLIENT)
        context.load_verify_locations('certificate.pem')
        context.set_alpn_protocols(protocols)
        #context.check_hostname = False # Does not verify the server name

        # Create a TCP/IP socket
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        with context.wrap_socket(sock, server_hostname='Unknown') as ssock: # The name that has been put in the server certificate must be entered
            ssock.connect(('localhost', 7070)) # ip and port of the server

            # To send message to the server
            output = ssock.makefile('w')

            username = simpledialog.askstring('Username', 'Introduzca su nombre de usuario:')
            output.write(username + '\n')

            password = simpledialog.askstring('Password', 'Introduzca su contraseña:')
            output.write(password + '\n')

            msg = simpledialog.askstring('Message', 'Introduzca su mensaje:')
            output.write(msg + '\n')

            output.flush()

            # Read the server response
            input_data = ssock.makefile('r')
            response = input_data.readline()

            messagebox.showinfo('Response', response)

            # The connection is automatically closed when leaving the "with" block
            
if __name__ == '__main__':
    BYODClient()
