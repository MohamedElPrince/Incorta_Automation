#!/usr/bin/env python
# -*- coding: utf-8 -*-

from setuptools import setup
#from distutils.core import setup

with open('README.rst') as readme_file:
    readme = readme_file.read()

with open('HISTORY.rst') as history_file:
    history = history_file.read()

requirements = [
    # TODO: put package requirements here
    'lxml>=3.6.1',
    'pip==8.1.2',
    'wheel==0.29'

]

test_requirements = [
    # TODO: put package test requirements here
]

setup(
    name='qa',
    version='0.1.0',
    description="Automation Framework for Incorta",
    long_description=readme + '\n\n' + history,
    author="Anahit Sarao",
    author_email='anahit.sarao@incorta.com',
    url='https://github.com/Incorta/qa',
    packages=[
        'qa',
        'qa.config',
        'qa.config.settings',
        'qa.Auto_Module',
    ],
    package_dir={'qa':
                 'qa'},
    include_package_data=True,
    install_requires=requirements,
    zip_safe=False,
    keywords='qa',
    classifiers=[
        'Development Status :: 2 - Pre-Alpha',
        'Intended Audience :: Internal Testing',
        'Natural Language :: English',
        'License:: Licensed Material - Property of Incorta. Proprietary License',
        'Framework :: Custom Build for Incorta Software'
        'Operating System :: MacOS',
        "Programming Language :: Python :: 2.7 :: Only",
        'Programming Language :: Python :: 2.6 :: Testing In Progress',
        'Programming Language :: Python :: 3   :: Testing In Progress',
        'Programming Language :: Python :: 3.3 :: Testing In Progress',
        'Programming Language :: Python :: 3.4 :: Testing In Progress',
        'Programming Language :: Python :: 3.5 :: Testing In Progress',
    ],
    test_suite='tests',
    tests_require=test_requirements,
)
