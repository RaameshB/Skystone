!pip install tensorflow
!pip install --upgrade grpcio
!pip install --upgrade google-auth
!pip install --upgrade tensorflow

import tensorflow as tf
#tf.logging.set_verbosity(tf.logging.ERROR)
import numpy as np


cel_q = np.array([0,20,30,40,50], dtype=float)
fah_a = np.array([32,68,86,104,122], dtype=float)

for i,c in enumerate (cel_q):
  print ("{} deg cel = {} deg fahr".format(c,fah_a[i]))

l0 = tf.keras.layers.Dense(units=1, input_shape=[1]) 
model = tf.keras.Sequential([l0])
model.compile(loss='mean_squared_error', optimizer=tf.keras.optimizers.Adam(0.6925))
history = model.fit(cel_q, fah_a, epochs=500, verbose=False)
model.predict([100.0])