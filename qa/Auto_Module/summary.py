from prettytable import PrettyTable
import sys
from customLogger import summaryLogger, writeLogMessage, DebugLevel


def print_table(*time_list, **test_suite_name_dict):
    """ """
    detail_table = PrettyTable()
    detail_header_table = PrettyTable()
    summary_header_table = PrettyTable()
    summary_table = PrettyTable()
    detail_header_table.field_names= ['Detailed Summary','-','--']
    detail_header_table.add_row(['Runtime',str(time_list[0])+' seconds',str(time_list[1])+' Minutes'])
    detail_table.field_names = ['Test Suites', 'Test Cases', '# of Suc Files', '# of Diff Files', 'Total File Count']

    summary_header_table.field_names = ['Overall Summary']
    summary_table.field_names = ['Test Suites', 'Pass Rate', 'Status']

    for suite_name, values in test_suite_name_dict.iteritems():
        overall_total = 0
        overall_exception_files = 0
        overall_suc_count = 0
        overall_diff_count = 0
        for case_name, files in values.iteritems():
            total = 0
            exception_files = 0
            suc_count = 0
            diff_count= 0
            for file in files:
                total += 1
                if file.endswith('.suc'):
                    suc_count += 1
                elif file.endswith('.diff'):
                    diff_count += 1
                else:
                    print 'Unknown file found inside Directory -- Invoke Pass Exception'
                    exception_files += 1
            overall_total += total
            overall_exception_files += exception_files
            overall_suc_count += suc_count
            overall_diff_count += diff_count
            detail_table.add_row([suite_name, case_name, suc_count, diff_count, total])
        try:
            pass_rate = (float((overall_suc_count + overall_diff_count + overall_exception_files) / overall_total)) * 100
            if pass_rate < 100:
                status = 'Failed'
            else:
                status = 'Success'
            summary_table.add_row([suite_name, pass_rate, status])
        except ZeroDivisionError as zerrno:
            print "\nI/O error({0})".format(zerrno.args)
            print '\nZero Division Exception, No Ouput Files found or No tests ran'
            print '\nDetailed Summary Shutting Down'
        except:
            print "Unexpected error:", sys.exc_info()
            raise

    print '\n'
    print detail_header_table
    print detail_table
    print '\n'
    print summary_header_table
    print summary_table
    writeLogMessage(detail_header_table,summaryLogger,DebugLevel.INFO)
    writeLogMessage(detail_table, summaryLogger, DebugLevel.INFO)
    writeLogMessage(summary_header_table, summaryLogger, DebugLevel.INFO)
    writeLogMessage(summary_table, summaryLogger, DebugLevel.INFO)


# print test_suite_name_list
# writeLogMessage(test_suite_name_list, summaryLogger, 'info')
# print "------------------------------------------Summary------------------------------------------------------"
# writeLogMessage(
#     "------------------------------------------Summary------------------------------------------------------",
#     summaryLogger, 'info')
#
# print "PERFORMANCE: "
# writeLogMessage("PERFORMANCE: ", summaryLogger, 'info')
# print "     Time Taken To Run Framework: ", (time.time() - start_time), " seconds"
# writeLogMessage("     Time Taken To Run Framework: %s seconds" % (time.time() - start_time), summaryLogger, 'info')
# minute_timer = (time.time() - start_time) / 60
# print "         In minutes... ", minute_timer
# writeLogMessage("         In minutes... %s" % minute_timer, summaryLogger, 'info')
#
# Total_Suite_Count = 0
# Passed_Suite_Count = 0
# Passed_Suite_List = []
# Failed_Suite_Count = 0
# Failed_Suite_List = []
# for suite in test_suite_name_list:
#     Total_Suite_Count += 1
#     print "\n|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
#     writeLogMessage('\n', summaryLogger, 'info')
#     writeLogMessage(
#         "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n",
#         summaryLogger, 'info')
#     print "\n"
#     print "-------------------------------------------------"
#     writeLogMessage("-------------------------------------------------", summaryLogger, 'info')
#
#     print suite, " ", "***RESULTS***"
#     temp_str = suite + " " + "***RESULTS***"
#     writeLogMessage(temp_str, summaryLogger, 'info')
#     print "-------------------------------------------------"
#     writeLogMessage("-------------------------------------------------", summaryLogger, 'info')
#     print "\n"
#     writeLogMessage('\n', summaryLogger, 'info')
#     print "             DATA VALIDATION"
#     writeLogMessage("             DATA VALIDATION", summaryLogger, 'info')
#     json_suc_count = 0
#     json_dif_count = 0
#     json_total_count = 0
#     test_suite_check = True
#     print "\n"
#     writeLogMessage('\n', summaryLogger, 'info')
#     if suite in test_suite_name_dict.keys():
#         temp_dict = test_suite_name_dict[suite]
#         case_check = True
#         for item, value in temp_dict.items():
#             failed_files = []
#             case_check = True
#             for file in value:
#                 if '.dif' in file:
#                     case_check = False
#                     failed_files.append(file)
#             result = ' '
#             if case_check == False:
#                 result = 'failed'
#                 json_dif_count += 1
#             else:
#                 result = 'successful'
#                 json_suc_count += 1
#             print "Test Case: ", item, " > ", result
#             writeLogMessage("Test Case: %s > %s" % (item, result), summaryLogger, 'info')
#             if result == 'failed':
#                 print "     FAILED FILES: ", failed_files
#                 writeLogMessage("     FAILED FILES: %s" % failed_files, summaryLogger, 'warning')
#             json_total_count += 1
#             if case_check == False:
#                 test_suite_check = False
#
#         if test_suite_check == False:
#             suite_result = 'FAILED'
#         else:
#             suite_result = 'SUCCESSFUL'
#         print "\nTEST SUITE: ", suite, ' > ', suite_result
#         writeLogMessage("TEST SUITE: %s > %s" % (suite, suite_result), summaryLogger, 'info')
#
#         DATA_VALID_SUCC = test_suite_check
#
#     json_pass_rate = (float(json_suc_count) / float(json_total_count)) * 100
#     print "\nJSON DATA VALIDATION TEST SUITE ", suite, ": ", "Failure Count: ", json_dif_count, " Success Count: ", json_suc_count, "   DATA PASS RATE: ", json_pass_rate, "%\n"
#     temp_str = "\n\nJSON DATA VALIDATION TEST SUITE " + str(suite) + ": " + "Failure Count: " + str(
#         json_dif_count) + " Success Count: " + str(json_suc_count) + "   DATA PASS RATE: " + str(json_pass_rate) + "%\n"
#     writeLogMessage(temp_str, summaryLogger, 'info')
#     print "**************************************************"
#     writeLogMessage("**************************************************", summaryLogger, 'info')
#     print '\n'
#     writeLogMessage('\n', summaryLogger, 'info')
#     print "             METADATA VALIDATION\n"
#     writeLogMessage("             METADATA VALIDATION\n", summaryLogger, 'info')
#     test_suite_check = True
#
#     if suite in metadata_suite_dict.keys():
#         if test_suite_check == False:
#             suite_result = 'FAILED'
#         else:
#             suite_result = 'SUCCESSFUL'
#         temp_dict = metadata_suite_dict[suite]
#         case_check = True
#         print "TEST SUITE: ", suite, ' > ', suite_result
#         writeLogMessage("TEST SUITE: %s > %s" % (suite, suite_result), summaryLogger, 'info')
#         for item, value in temp_dict.items():
#             failed_files = []
#             case_check = True
#             for file in value:
#                 if '.dif' in file:
#                     case_check = False
#                     failed_files.append(file)
#             result = ' '
#             if case_check == False:
#                 result = 'failed'
#             else:
#                 result = 'successful'
#             print "MetaData Type: ", item, " > ", result
#             writeLogMessage("MetaData Type: %s > %s" % (item, result), summaryLogger, 'info')
#             if result == 'failed':
#                 print "     FAILED FILES: ", failed_files
#                 writeLogMessage("     FAILED FILES: %s" % failed_files, summaryLogger, 'warning')
#             if case_check == False:
#                 test_suite_check = False
#
#         METADATA_VALID_SUCC = test_suite_check
#
#     print "\n**************************************************"
#     writeLogMessage('\n\n', summaryLogger, 'info')
#     writeLogMessage("**************************************************", summaryLogger, 'info')
#     print "\n"
#     writeLogMessage('\n', summaryLogger, 'info')
#     print "             LOADER VALIDATION\n"
#     writeLogMessage("             LOADER VALIDATION\n", summaryLogger, 'info')
#     loader_test_suite_check = True
#     failed_schemas = []
#     loader_result = ''
#     if suite in loader_valid_dict.keys():
#         for schema in loader_valid_dict[suite]:
#             if '.dif' in schema:
#                 loader_test_suite_check = False
#                 failed_schemas.append(failed_schemas)
#         if loader_test_suite_check == False:
#             loader_result = 'FAILED'
#         else:
#             loader_result = 'SUCCESSFUL'
#     else:
#         loader_result = 'MISSING'
#     print "TEST SUITE: ", suite, " ", loader_result
#     writeLogMessage("TEST SUITE: %s %s" % (suite, loader_result), summaryLogger, 'info')
#     if loader_result == 'FAILED':
#         print "Schemas Failed to Load... ", failed_schemas
#         writeLogMessage("Schemas Failed to Load... %s" % failed_schemas, summaryLogger, 'warning')
#     LOAD_VALID_SUCC = loader_test_suite_check
#
#     print "\n**************************************************"
#     writeLogMessage('\n', summaryLogger, 'info')
#     writeLogMessage("**************************************************\n", summaryLogger, 'info')
#     SUITE_STATUS = ''
#     if DATA_VALID_SUCC and METADATA_VALID_SUCC and LOAD_VALID_SUCC == True:
#         SUITE_STATUS = 'PASSED'
#     else:
#         SUITE_STATUS = 'FAILED'
#
#     print "\n             ", suite, " Summary\n"
#     writeLogMessage("             %s Summary\n" % suite, summaryLogger, 'info')
#     print "Overall test suite ", suite, " ", SUITE_STATUS, "\n"
#     writeLogMessage("Overall test suite %s %s \n" % (suite, SUITE_STATUS), summaryLogger, 'info')
#     failed_phases = []
#     if SUITE_STATUS == 'FAILED':
#         if not DATA_VALID_SUCC:
#             failed_phases.append('Data Validation Phase')
#         if not METADATA_VALID_SUCC:
#             failed_phases.append('MetaData Validation Phase')
#         if not LOAD_VALID_SUCC:
#             failed_phases.append('Schema Loader Validation Phase')
#         print suite, " failed in these validation phases; ", failed_phases
#         writeLogMessage(" failed in these validation phases; %s" % failed_phases, summaryLogger, 'info')
#         Failed_Suite_Count += 1
#         Failed_Suite_List.append(suite)
#     else:
#         Passed_Suite_Count += 1
#         Passed_Suite_List.append(suite)
#
# print "************************************** TEST RESULTS *************************************"
# writeLogMessage("************************************** TEST RESULTS *************************************",
#                 summaryLogger, 'info')
#
# print "Out of ", Total_Suite_Count, " tested suites,   ", Passed_Suite_Count, "passed testing      ", Failed_Suite_Count, "failed testing ", "\n"
# temp_str = "Out of " + str(Total_Suite_Count) + " tested suites,   " + str(
#     Passed_Suite_Count) + " passed testing      " + str(Failed_Suite_Count) + " failed testing " + "\n"
# writeLogMessage(temp_str, summaryLogger, 'info')
# if Passed_Suite_Count > 0:
#     print "List of successful test suites: ", Passed_Suite_List
#     writeLogMessage("List of successful test suites: %s" % Passed_Suite_List, summaryLogger, 'info')
#
# if Failed_Suite_Count > 0:
#     print "List of failed test suites: ", Failed_Suite_List
#     writeLogMessage("List of failed test suites: %s" % Failed_Suite_List, summaryLogger, 'warning')
#
# Suite_pass_rate = (float(Passed_Suite_Count) / float(Total_Suite_Count)) * 100
# Suite_pass_rate = int(Suite_pass_rate)
# print "Incorta Build has a ", Suite_pass_rate, "% success rate"
# temp_str = "Incorta Build has a " + str(Suite_pass_rate) + "% success rate"
# writeLogMessage(temp_str, summaryLogger, 'info')
#
# time.sleep(2)
# shutdown_logger(mainLogger)
# shutdown_logger(summaryLogger)
