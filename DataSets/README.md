DataSets
===============

In this directory, please find the various datasets used for our benchmark. These datasets numeroted from 1 to 10 reflect from best to worst case scenario the usefulness of our approach. Each datasets is attached to a kind of sensors and is issued from an arduino based monitoring during many months.

Many values of this experimental dataset are coming from the open source work which can be followed at this address: http://www.kayacik.ca/datasets/arduino/.

## Used sensors:

Temperature: DHT11 (0'C~50'C +/- 2'C)
Luminosity: SEN-09088 (10 lux précision)
Electric sensor: SmartMeter

## Common format

All datasets follow the same format. File are CSV file with one record per row. In the following format:

TIMESTAMP,VALUE;

### Constant dataset
12,000,000 values.
- DS0 (aka const_db) : constant = 24.0.

### Temperature sensor(value = celcius degree)
11,419,895 values.
- DS1 (aka temp_ord) : Weekly monitoring every second of the tempoerature of a room. Ordered on the natural temporal order.
- DS2 (aka temp_ran) : Weekly monitoring every second of the tempoerature of a room. Ordered on a chaotic temporal order.

### Luminosity sensor(value = lux)
11,419,895 values.
- DS3 (aka lux_ord) : Weekly monitoring every second of the luminosity of a room. Ordered on the natural temporal order.
- DS4 (aka lux_ran) : Weekly monitoring every second of the luminosity of a room. Ordered on a chaotic temporal order.

### Electric consumption sensor(value = w/h)
3,506,304 values.
- DS5 (aka lux_ord) : Weekly monitoring every quarter of the electric consumption of an house. Ordered on the natural temporal order.
- DS6 (aka lux_ran) : Weekly monitoring every quarter of the electric consumption of an house. Ordered on a chaotic temporal order.

### Sound sensor(value = db)
3,492,720 values.
- DS7 (aka snd_sens_ord) : Weekly monitoring every second of the sound level of a room. Ordered on the natural temporal order.
- DS8 (aka snd_sens_ran) : Weekly monitoring every second of the sound level of a room. Ordered on a chaotic temporal order.
- DS9 (aka snd_music_ord) : 3 minutes music file converted into a set of sounds using a 44000 sampling rates. Inserted following the natural temportal order. The original music file is an open source wav "UncleBibby_-_06_-_Punch_It.wav"
- DS10 (aka snd_music_ran) : 3 minutes music file converted into a set of sounds using a 44000 sampling rates. Inserted following a chaotic temportal order. The original music file is an open source wav "UncleBibby_-_06_-_Punch_It.wav"

### Full Random(generated from random.org)
12,000,000 values.
- DS11 (aka_Full_Random): Random doubles generated using the Mersenne Twister RNG.