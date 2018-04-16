import pygame
import time
import socket
import sys
import gpiozero as gpio
import RPi.GPIO as GPIO
import _thread as thread
from array import array
from connect import *
from pygame.locals import *
from tcpmorse_lookup import *

pygame.mixer.pre_init(44100, -16, 1, 1024)
pygame.init()

class ToneSound(pygame.mixer.Sound):
    def __init__(self, frequency, volume):
        self.frequency = frequency
        pygame.mixer.Sound.__init__(self, self.build_samples())
        self.set_volume(volume)

    def build_samples(self):
        period = int(round(pygame.mixer.get_init()[0] / self.frequency))
        samples = array("h", [0] * period)
        amplitude = 2 ** (abs(pygame.mixer.get_init()[1]) - 1) - 1
        for time in range(period):
            if time < period / 2:
                samples[time] = amplitude
            else:
                samples[time] = -amplitude
        return samples

def decoder_thread():
    global key_up_time
    global buffer
    new_word = False
    while True:
        time.sleep(.01)
        key_up_length = time.time() - key_up_time
        if len(buffer) > 0 and key_up_length >= 1.5:
            new_word = True
            bit_string = "".join(buffer)
            try_decode(bit_string, sock)
            del buffer[:]
        elif new_word and key_up_length >= 3.5:
            new_word = False
            data = " "
            sock.send(data.encode())


def socket_thread():
        while True:
                data = sock.recv(16)
                sys.stdout.write(data.decode())
                conn = connect2db()
                msgdb(conn, data.decode(),2,1)
                connectionclose(conn)
                sys.stdout.flush()


tone_obj = ToneSound(frequency = 1000, volume = 0.2)

sock = socket.create_connection((sys.argv[1] , 40070))

pin = 27

key = gpio.Button(pin , pull_up=True)
DOT = "."
DASH = "-"

key_down_time = 0
key_down_length = 0
key_up_time = 0
buffer = []

thread.start_new_thread(decoder_thread,())
thread.start_new_thread(socket_thread,())

print("Ready")

while True:
        key.wait_for_press()
        key_down_time = time.time()
        tone_obj.play(-1)
        key.wait_for_release()
        key_up_time = time.time()
        key_down_length = key_up_time - key_down_time
        tone_obj.stop()
        if(key_down_length > 0.15):
         buffer.append(DASH)
        elif(key_down_length > 0.01):
         buffer.append(DOT)