import argparse
import os
from errno import ENOENT
import csv

ROOT_DIR = os.path.dirname(__file__)  # This is your Project Root


def check_logfile(file):
    """
    Check for logfile, at given path.
    """
    if not os.path.isfile(file):
        raise IOError(ENOENT, 'Not a file', file)


def parse_logfile(file, schema_name):
    """
    """
    with open(file) as fp2:
        content = fp2.read()
    # print("There are {}, Schemas Loaded in {}").format(content.count("Finished"), file)  # Extra detail -- Fun

    data = []
    parsed_data = []
    with open(file) as fp:
        for line in fp:
            line = line.strip()  # Preprocess line
            data.append(line)

    for i in data:
        if "Finished" in i:
            if "INFO" in i:
                if (schema_name + "]") in i:
                    parsed_data.append(i.split(","))

    filtered_data = []
    for i in parsed_data:
        for i2 in i:
            filtered_data.append(i2.split(" "))
    csv_writer(filtered_data)


def csv_writer(data, path="output.csv"):
    """
    Write data to a CSV file path
    """
    with open(path, "wb") as csv_file:
        writer = csv.writer(csv_file, delimiter=',')
        for line in data:
            writer.writerow(line)

def main():
    """
    Main function handles arguments
    """
    print("Sample Input: -f /home/incorta/Incorta.2017-09-24.log -s jira-incremental")
    parser = argparse.ArgumentParser(description='Description for Custom LogParser',
                                     formatter_class=argparse.RawTextHelpFormatter)
    parser.add_argument('-f', '--file', help='Logfile to be Parsed. \nSample Input: /home/incorta/incorta.2017.log',
                        required=True)
    parser.add_argument('-s', '--schema', help='Schema name. \nSample Input: jira-incremental', required=True)
    args = parser.parse_args()
    # print('Current Arguments: {0:2}').format(args)
    check_logfile(args.file)
    parse_logfile(args.file, args.schema)


if __name__ == '__main__':
    """Main"""
    main()
