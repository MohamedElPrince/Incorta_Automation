import logging, os

def setup_logger(logger_name, log_file, level=logging.INFO):
    l = logging.getLogger(logger_name)
    formatter = logging.Formatter('%(asctime)s : %(message)s')
    fileHandler = logging.FileHandler(log_file, mode='w')
    fileHandler.setFormatter(formatter)
    streamHandler = logging.StreamHandler()
    streamHandler.setFormatter(formatter)

    l.setLevel(level)
    l.addHandler(fileHandler)
    l.addHandler(streamHandler)

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

# Create Log file Object with proper handlers

def setup(logger_name1, log_file1, level=logging.INFO):
    logger_name2 = logger_name1
    log_file2 = log_file1
    global logger_name2
    global log_file2
logObject = setup_logger(logger_name2, log_file2)

