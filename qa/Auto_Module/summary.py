from prettytable import PrettyTable
import sys


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
    overall_total = 0
    overall_exception_files = 0
    overall_suc_count = 0
    overall_diff_count = 0
    pass_rate = 0
    status = 'Success'
    for suite_name, values in test_suite_name_dict.iteritems():
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
                    diff_count +=1
                else:
                    print 'Unknown file found inside Directory -- Invoke Pass Exception'
                    exception_files =+ 1
            overall_total += total
            overall_exception_files += exception_files
            overall_suc_count += suc_count
            overall_diff_count += diff_count
            detail_table.add_row([suite_name, case_name, suc_count, diff_count, total])
        try:
            pass_rate = (float((overall_suc_count + overall_diff_count + overall_exception_files) / overall_total)) * 100
            if pass_rate < 100:
                status = 'Failed'
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