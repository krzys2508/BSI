from Crypto.Cipher import DES
import threading

message = b"M\xe9\x07M\x0c\x1f3\xa0\x88L\x08\xde\x9c[\xac\x97;\xe3\xac\x02z\xde\xc7'\xcdx+\xfc\x9b\x0fcr \xed\x0fX\xab\xe7\xed\xba}\xefP\x90-\x92\xb9\xe4+\xc5\xc0\xc4\xc1\x01\xd1oZ\x1eD*\xc6\xf6Ze\xe1\xc8i\xec\x94\xa9\xf0~\xa3\xf1\r]6M\x97\xc8\x80\xce\x1a\x0f\xc9Ky\xd0\x07c=YL\xc1\xff\xb99b\x08\xda\xce\x93\x05\xfc\xe3\x8c\x11\xf7w\xce\xec$\xceu\xb8\xbf\xd5xI\x97\xe1\xe4\xfaK\x11\xb2z\xaaP,Q\xe7\xb7<\xef7\xff\x862\xd0dz^\xb5\r\x89\x8a\xd2d\x8d\xc8\xe7q\xe7\x1e89\x9aN9\x11\xdaHX\xb6\xb0\xe2\x8b^Z\xd3\x87P\xe7;\x87\xd1z^\xa3!\x12g*f\xb1Fe\xdd\x1a\xeb\xde\xc3\x8d\xfd\x99\x7f\xb7\xc4\xdd\x94\xf6\xdd\xd71\x9b\x0e\x04:\x18-\xee}\x0f\xa8#\x15ff\x9a\x9d\xfb\x8fl"
threads = []
swear_words = ['chuj', 'chuja', 'chujek', 'chuju', 'chujem', 'chujnia',
               'chujowy', 'chujowa', 'chujowe', 'cipa', 'cipę', 'cipe', 'cipą',
               'cipie', 'dojebać', 'dojebac', 'dojebie', 'dojebał', 'dojebal',
               'dojebała', 'dojebala', 'dojebałem', 'dojebalem', 'dojebałam',
               'dojebalam', 'dojebię', 'dojebie', 'dopieprzać', 'dopieprzac',
               'dopierdalać', 'dopierdalac', 'dopierdala', 'dopierdalał',
               'dopierdalal', 'dopierdalała', 'dopierdalala', 'dopierdoli',
               'dopierdolił', 'dopierdolil', 'dopierdolę', 'dopierdole', 'dopierdoli',
               'dopierdalający', 'dopierdalajacy', 'dopierdolić', 'dopierdolic',
               'dupa', 'dupie', 'dupą', 'dupcia', 'dupeczka', 'dupy', 'dupe', 'huj',
               'hujek', 'hujnia', 'huja', 'huje', 'hujem', 'huju', 'jebać', 'jebac',
               'jebał', 'jebal', 'jebie', 'jebią', 'jebia', 'jebak', 'jebaka', 'jebal',
               'jebał', 'jebany', 'jebane', 'jebanka', 'jebanko', 'jebankiem',
               'jebanymi', 'jebana', 'jebanym', 'jebanej', 'jebaną', 'jebana',
               'jebani', 'jebanych', 'jebanymi', 'jebcie', 'jebiący', 'jebiacy',
               'jebiąca', 'jebiaca', 'jebiącego', 'jebiacego', 'jebiącej', 'jebiacej',
               'jebia', 'jebią', 'jebie', 'jebię', 'jebliwy', 'jebnąć', 'jebnac',
               'jebnąc', 'jebnać', 'jebnął', 'jebnal', 'jebną', 'jebna', 'jebnęła',
               'jebnela', 'jebnie', 'jebnij', 'jebut', 'koorwa', 'kórwa', 'kurestwo',
               'kurew', 'kurewski', 'kurewska', 'kurewskiej', 'kurewską', 'kurewska',
               'kurewsko', 'kurewstwo', 'kurwa', 'kurwaa', 'kurwami', 'kurwą', 'kurwe',
               'kurwę', 'kurwie', 'kurwiska', 'kurwo', 'kurwy', 'kurwach', 'kurwami',
               'kurewski', 'kurwiarz', 'kurwiący', 'kurwica', 'kurwić', 'kurwic',
               'kurwidołek', 'kurwik', 'kurwiki', 'kurwiszcze', 'kurwiszon',
               'kurwiszona', 'kurwiszonem', 'kurwiszony', 'kutas', 'kutasa', 'kutasie',
               'kutasem', 'kutasy', 'kutasów', 'kutasow', 'kutasach', 'kutasami',
               'matkojebca', 'matkojebcy', 'matkojebcą', 'matkojebca', 'matkojebcami',
               'matkojebcach', 'nabarłożyć', 'najebać', 'najebac', 'najebał',
               'najebal', 'najebała', 'najebala', 'najebane', 'najebany', 'najebaną',
               'najebana', 'najebie', 'najebią', 'najebia', 'naopierdalać',
               'naopierdalac', 'naopierdalał', 'naopierdalal', 'naopierdalała',
               'naopierdalala', 'naopierdalała', 'napierdalać', 'napierdalac',
               'napierdalający', 'napierdalajacy', 'napierdolić', 'napierdolic',
               'nawpierdalać', 'nawpierdalac', 'nawpierdalał', 'nawpierdalal',
               'nawpierdalała', 'nawpierdalala', 'obsrywać', 'obsrywac', 'obsrywający',
               'obsrywajacy', 'odpieprzać', 'odpieprzac', 'odpieprzy', 'odpieprzył',
               'odpieprzyl', 'odpieprzyła', 'odpieprzyla', 'odpierdalać',
               'odpierdalac', 'odpierdol', 'odpierdolił', 'odpierdolil',
               'odpierdoliła', 'odpierdolila', 'odpierdoli', 'odpierdalający',
               'odpierdalajacy', 'odpierdalająca', 'odpierdalajaca', 'odpierdolić',
               'odpierdolic', 'odpierdoli', 'odpierdolił', 'opieprzający',
               'opierdalać', 'opierdalac', 'opierdala', 'opierdalający',
               'opierdalajacy', 'opierdol', 'opierdolić', 'opierdolic', 'opierdoli',
               'opierdolą', 'opierdola', 'piczka', 'pieprznięty', 'pieprzniety',
               'pieprzony', 'pierdel', 'pierdlu', 'pierdolą', 'pierdola', 'pierdolący',
               'pierdolacy', 'pierdoląca', 'pierdolaca', 'pierdol', 'pierdole',
               'pierdolenie', 'pierdoleniem', 'pierdoleniu', 'pierdolę', 'pierdolec',
               'pierdola', 'pierdolą', 'pierdolić', 'pierdolicie', 'pierdolic',
               'pierdolił', 'pierdolil', 'pierdoliła', 'pierdolila', 'pierdoli',
               'pierdolnięty', 'pierdolniety', 'pierdolisz', 'pierdolnąć',
               'pierdolnac', 'pierdolnął', 'pierdolnal', 'pierdolnęła', 'pierdolnela',
               'pierdolnie', 'pierdolnięty', 'pierdolnij', 'pierdolnik', 'pierdolona',
               'pierdolone', 'pierdolony', 'pierdołki', 'pierdzący', 'pierdzieć',
               'pierdziec', 'pizda', 'pizdą', 'pizde', 'pizdę', 'piździe', 'pizdzie',
               'pizdnąć', 'pizdnac', 'pizdu', 'podpierdalać', 'podpierdalac',
               'podpierdala', 'podpierdalający', 'podpierdalajacy', 'podpierdolić',
               'podpierdolic', 'podpierdoli', 'pojeb', 'pojeba', 'pojebami',
               'pojebani', 'pojebanego', 'pojebanemu', 'pojebani', 'pojebany',
               'pojebanych', 'pojebanym', 'pojebanymi', 'pojebem', 'pojebać',
               'pojebac', 'pojebalo', 'popierdala', 'popierdalac', 'popierdalać',
               'popierdolić', 'popierdolic', 'popierdoli', 'popierdolonego',
               'popierdolonemu', 'popierdolonym', 'popierdolone', 'popierdoleni',
               'popierdolony', 'porozpierdalać', 'porozpierdala', 'porozpierdalac',
               'poruchac', 'poruchać', 'przejebać', 'przejebane', 'przejebac',
               'przyjebali', 'przepierdalać', 'przepierdalac', 'przepierdala',
               'przepierdalający', 'przepierdalajacy', 'przepierdalająca',
               'przepierdalajaca', 'przepierdolić', 'przepierdolic', 'przyjebać',
               'przyjebac', 'przyjebie', 'przyjebała', 'przyjebala', 'przyjebał',
               'przyjebal', 'przypieprzać', 'przypieprzac', 'przypieprzający',
               'przypieprzajacy', 'przypieprzająca', 'przypieprzajaca',
               'przypierdalać', 'przypierdalac', 'przypierdala', 'przypierdoli',
               'przypierdalający', 'przypierdalajacy', 'przypierdolić',
               'przypierdolic', 'qrwa', 'rozjebać', 'rozjebac', 'rozjebie',
               'rozjebała', 'rozjebią', 'rozpierdalać', 'rozpierdalac', 'rozpierdala',
               'rozpierdolić', 'rozpierdolic', 'rozpierdole', 'rozpierdoli',
               'rozpierducha', 'skurwić', 'skurwiel', 'skurwiela', 'skurwielem',
               'skurwielu', 'skurwysyn', 'skurwysynów', 'skurwysynow', 'skurwysyna',
               'skurwysynem', 'skurwysynu', 'skurwysyny', 'skurwysyński',
               'skurwysynski', 'skurwysyństwo', 'skurwysynstwo', 'spieprzać',
               'spieprzac', 'spieprza', 'spieprzaj', 'spieprzajcie', 'spieprzają',
               'spieprzaja', 'spieprzający', 'spieprzajacy', 'spieprzająca',
               'spieprzajaca', 'spierdalać', 'spierdalac', 'spierdala', 'spierdalał',
               'spierdalała', 'spierdalal', 'spierdalalcie', 'spierdalala',
               'spierdalający', 'spierdalajacy', 'spierdolić', 'spierdolic',
               'spierdoli', 'spierdoliła', 'spierdoliło', 'spierdolą', 'spierdola',
               'srać', 'srac', 'srający', 'srajacy', 'srając', 'srajac', 'sraj',
               'sukinsyn', 'sukinsyny', 'sukinsynom', 'sukinsynowi', 'sukinsynów',
               'sukinsynow', 'śmierdziel', 'udupić', 'ujebać', 'ujebac', 'ujebał',
               'ujebal', 'ujebana', 'ujebany', 'ujebie', 'ujebała', 'ujebala',
               'upierdalać', 'upierdalac', 'upierdala', 'upierdoli', 'upierdolić',
               'upierdolic', 'upierdoli', 'upierdolą', 'upierdola', 'upierdoleni',
               'wjebać', 'wjebac', 'wjebie', 'wjebią', 'wjebia', 'wjebiemy',
               'wjebiecie', 'wkurwiać', 'wkurwiac', 'wkurwi', 'wkurwia', 'wkurwiał',
               'wkurwial', 'wkurwiający', 'wkurwiajacy', 'wkurwiająca', 'wkurwiajaca',
               'wkurwić', 'wkurwic', 'wkurwi', 'wkurwiacie', 'wkurwiają', 'wkurwiali',
               'wkurwią', 'wkurwia', 'wkurwimy', 'wkurwicie', 'wkurwiacie', 'wkurwić',
               'wkurwic', 'wkurwia', 'wpierdalać', 'wpierdalac', 'wpierdalający',
               'wpierdalajacy', 'wpierdol', 'wpierdolić', 'wpierdolic', 'wpizdu',
               'wyjebać', 'wyjebac', 'wyjebali', 'wyjebał', 'wyjebac', 'wyjebała',
               'wyjebały', 'wyjebie', 'wyjebią', 'wyjebia', 'wyjebiesz', 'wyjebie',
               'wyjebiecie', 'wyjebiemy', 'wypieprzać', 'wypieprzac', 'wypieprza',
               'wypieprzał', 'wypieprzal', 'wypieprzała', 'wypieprzala', 'wypieprzy',
               'wypieprzyła', 'wypieprzyla', 'wypieprzył', 'wypieprzyl', 'wypierdal',
               'wypierdalać', 'wypierdalac', 'wypierdala', 'wypierdalaj',
               'wypierdalał', 'wypierdalal', 'wypierdalała', 'wypierdalala',
               'wypierdalać', 'wypierdolić', 'wypierdolic', 'wypierdoli',
               'wypierdolimy', 'wypierdolicie', 'wypierdolą', 'wypierdola',
               'wypierdolili', 'wypierdolił', 'wypierdolil', 'wypierdoliła',
               'wypierdolila', 'zajebać', 'zajebac', 'zajebie', 'zajebią', 'zajebia',
               'zajebiał', 'zajebial', 'zajebała', 'zajebiala', 'zajebali', 'zajebana',
               'zajebani', 'zajebane', 'zajebany', 'zajebanych', 'zajebanym',
               'zajebanymi', 'zajebiste', 'zajebisty', 'zajebistych', 'zajebista',
               'zajebistym', 'zajebistymi', 'zajebiście', 'zajebiscie', 'zapieprzyć',
               'zapieprzyc', 'zapieprzy', 'zapieprzył', 'zapieprzyl', 'zapieprzyła',
               'zapieprzyla', 'zapieprzą', 'zapieprza', 'zapieprzy', 'zapieprzymy',
               'zapieprzycie', 'zapieprzysz', 'zapierdala', 'zapierdalać',
               'zapierdalac', 'zapierdalaja', 'zapierdalał', 'zapierdalaj',
               'zapierdalajcie', 'zapierdalała', 'zapierdalala', 'zapierdalali',
               'zapierdalający', 'zapierdalajacy', 'zapierdolić', 'zapierdolic',
               'zapierdoli', 'zapierdolił', 'zapierdolil', 'zapierdoliła',
               'zapierdolila', 'zapierdolą', 'zapierdola', 'zapierniczać',
               'zapierniczający', 'zasrać', 'zasranym', 'zasrywać', 'zasrywający',
               'zesrywać', 'zesrywający', 'zjebać', 'zjebac', 'zjebał', 'zjebal',
               'zjebała', 'zjebala', 'zjebana', 'zjebią', 'zjebali', 'zjeby']

def iterate(buff: str, depth: int, decrypt, check, saveFile, start_depth):
    '''
    checks the keys
    Inputs:
    buff - start of the message
    depth - how many characters are missing
    decrypt - decryptor
    check - swearword checker
    hook - file to save the message to
    Returns:
    None
    '''

    if depth == 0:
        check(decrypt(buff), buff, swear_words, saveFile)
        return
    for c in range(97, 123):
        if depth == start_depth:
            thread = threading.Thread(target=iterate(buff + chr(c), depth - 1, decrypt, check, saveFile, start_depth))
            threads.append(thread)
            thread.start()
        else:
            iterate(
                buff + chr(c),
                depth - 1,
                decrypt,
                check,
                saveFile,
                start_depth
            )


def contains_a_swear_word(msg: str, key, swearWords: list, saveFile):
    '''
    Tests if message contains swear words. Saves the message if so
    
    Inputs:
    msg - message to be tested
    swearWords - list of swearwords
    hook - file to save the message in
    
    Returns:
    bolean - true if the message contains a swearword, false otherwise
    '''
    for item in swearWords:
        if item in msg:
            print(f"The supposed secret message is : {msg}\nThe supposed key is {key}\n", file=saveFile)
            return True
    return False


def decrypt_message(supposed_key: str, msgNeedsToBeDecryptedt=message):
    '''
    decrypts the message with a key
    Inputs:
    supposed_key - key to test
    message_to_decrypt - message to decrypt
    Returns:
    decoded message
    '''
    des = DES.new(supposed_key.encode(), DES.MODE_ECB)
    message_decoded = des.decrypt(msgNeedsToBeDecryptedt).decode(encoding='UTF-8', errors='ignore')
    return message_decoded
    print (message_decoded)


with open("results.txt", 'w', encoding="utf-8") as saveFile:
    iterate(
        "",
        8,
        decrypt_message,
        contains_a_swear_word,
        saveFile,
        8
    )

for thread in threads:
    thread.join()
