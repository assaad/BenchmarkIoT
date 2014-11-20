DataSets
===============

In this directory, please find the various datasets used for our benchmark. These datasets numeroted from 1 to 10 reflect from best and worse case scenario for our approach. Each datasets is attached to a kind of sensors and is issue from an arduino based monitoring during one week.

Many values of this experimental dataset are coming from the open source work which can be followed at this address: http://www.kayacik.ca/datasets/arduino/.

## Used sensors:

Temperature: DHT11 (0'C~50'C +/- 2'C)
Luminosity: SEN-09088 (10 lux précision)
Electric sensor: SmartMeter

## Common format

All datasets follow the same format. File are CSV file with one record per row. In the following forme:

TIMESTAMP,VALUE;

### Temperature sensor(value = celcius degree)

- DS1 (aka temp_ord) : Weekly monitoring every second of the tempoerature of a room. Ordered on the natural temporal order.
- DS2 (aka temp_ran) : Weekly monitoring every second of the tempoerature of a room. Ordered on a chaotic temporal order.

### Luminosity sensor(value = lux)

- DS3 (aka lux_ord) : Weekly monitoring every second of the luminosity of a room. Ordered on the natural temporal order.
- DS4 (aka lux_ran) : Weekly monitoring every second of the luminosity of a room. Ordered on a chaotic temporal order.

### Electric consumption sensor(value = w/h)

- DS5 (aka lux_ord) : Weekly monitoring every quarter of the electric consumption of an house. Ordered on the natural temporal order.
- DS6 (aka lux_ran) : Weekly monitoring every quarter of the electric consumption of an house. Ordered on a chaotic temporal order.

### Sound sensor(value = db)

- DS7 (aka snd_sens_ord) : Weekly monitoring every second of the sound level of a room. Ordered on the natural temporal order.
- DS8 (aka snd_sens_ran) : Weekly monitoring every second of the sound level of a room. Ordered on a chaotic temporal order.
- DS9 (aka snd_music_ord) : 3 minutes music file converted into a set of sounds using a 44000 sampling rates. Inserted following the natural temportal order. You can listen to the original music file "music.wav"
- DS10 (aka snd_music_ran) : 3 minutes music file converted into a set of sounds using a 44000 sampling rates. Inserted following a chaotic temportal order.
