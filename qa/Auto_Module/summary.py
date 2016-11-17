from prettytable import PrettyTable
import sys


def print_table(*time_list, **test_suite_name_dict):
    """ """
    detail_table = PrettyTable()
    header_table = PrettyTable()
    header_table.field_names= ['Detailed Summary','-','--']
    header_table.add_row(['Runtime',str(time_list[0])+' seconds',str(time_list[1])+' Minutes'])
    print header_table
    detail_table.field_names = ['Test Suites', 'Test Cases', '# of Suc Files', '# of Diff Files', 'Pass Rate']
    for suite_name, values in test_suite_name_dict.iteritems():
        for case_name, files in values.iteritems():
            total = 0
            exception_files = 0
            all_file_count = 0
            suc_count = 0
            diff_count= 0
            pass_rate = 0
            for file in files:
                total += 1
                if file.endswith('.suc'):
                    suc_count += 1
                elif file.endswith('.diff'):
                    diff_count +=1
                else:
                    print 'Unknown file found inside Directory -- Invoke Pass Exception'
                    exception_files =+ 1

            try:
                pass_rate = (float((suc_count + diff_count + exception_files)/total)) * 100
            except ZeroDivisionError as zerrno:
                print "\nI/O error({0})".format(zerrno.args)
                print '\nZero Division Exception, No Ouput Files found or No tests ran'
                print '\nDetailed Summary Shutting Down'
            except:
                print "Unexpected error:", sys.exc_info()
                raise

            detail_table.add_row([suite_name, case_name, suc_count, diff_count, pass_rate])

    print detail_table

# for suite, case in test_suite_name_dict.iteritems():
#     if suite != None:
#         name_list.append(suite)
#     else:
#         pass
# test_suite_name_list = [(str(x), str((x))) for x in test_suite_name_dict]
# col_width = [max(len(x) for x in col) for col in zip(*test_suite_name_list)]
# for line in test_suite_name_list:
#     print "| " + " | ".join("{:{}}".format(x, col_width[i])
#                             for i, x in enumerate(line)) + " |"
