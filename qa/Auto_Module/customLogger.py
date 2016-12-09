import logging
from qa.config.settings.initialization_global import *


class DebugLevel:
    # Different levels of logging to pass to writeLogMessage
    DEBUG = 'debug'
    INFO = 'info'
    WARNING = 'warning'
    ERROR = 'error'
    CRITICAL = 'critical'

    def __init__(self):
        self.DEBUG = 'debug'
        self.INFO = 'info'
        self.WARNING = 'warning'
        self.ERROR = 'error'
        self.CRITICAL = 'critical'

def setup_logger(logger_name, log_file, level=logging.DEBUG):
    l = logging.getLogger(logger_name)
    formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
    fileHandler = logging.FileHandler(log_file, mode='w')
    fileHandler.setFormatter(formatter)
    #streamHandler = logging.StreamHandler()
    #streamHandler.setFormatter(formatter)
    l.setLevel(level)
    l.addHandler(fileHandler)
    #l.addHandler(streamHandler)
    return l

def writeLogMessage(logMessage, logObject, level):
    if level == 'debug':
        logObject.debug(logMessage)
    if level == 'info':
        logObject.info(logMessage)
    if level == 'warning':
        logObject.warning(logMessage)
    if level == 'error':
        logObject.error(logMessage)
    if level == 'critical':
        logObject.critical(logMessage)


def shutdown_logger(logObject):
    handlers = logObject.handlers[:]
    for handler in handlers:
        handler.close()
        logObject.removeHandler(handler)

#Create LoggerObject
mainLogger = setup_logger('output', wd_path + os.sep + r'output.log')
summaryLogger = setup_logger('TEST_RESULTS', wd_path + os.sep + r'TEST_RESULTS.log')