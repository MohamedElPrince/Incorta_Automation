import sys

custom_hook = False


def exceptionHandler(exception_type, exception, traceback, debug_hook=sys.excepthook):
    if custom_hook:
        debug_hook(exception_type, exception, traceback)
    else:
        print "%s: %s" % (exception_type.__name__, exception)
