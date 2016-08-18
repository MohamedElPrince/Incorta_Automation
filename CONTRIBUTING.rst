.. highlight:: shell

============
Contributing
============

Contributions are welcome, and they are greatly appreciated! Every
little bit helps, and credit will always be given.

Ways to Contribute:

Types of Contributions
----------------------

Report Bugs
~~~~~~~~~~~

Report bugs at https://github.com/asarao/qa/issues.

If you are reporting a bug, please include:

* Your operating system name and version.
* Any details about your local setup that might be helpful in troubleshooting.
* Detailed steps to reproduce the bug.

Write Documentation
~~~~~~~~~~~~~~~~~~~

QA could always use more documentation, whether as part of the
official qa docs, in docstrings.

Submit Feedback
~~~~~~~~~~~~~~~

The best way to send feedback is to file an issue at https://github.com/asarao/qa/issues.

If you are proposing a feature:

* Explain in detail how it would work.
* Keep the scope narrow, for easier to implement.
* All contributions are welcome :)

Get Started!
------------

When you're done making changes, check that your changes pass flake8 and the tests, including testing other Python versions with tox::

    $ flake8 qa tests
    $ python setup.py test or py.test  #This is not working yet
    $ tox

Pull Request Guidelines
-----------------------

Before you submit a pull request, check that it meets these guidelines:

1. The pull request should include tests.
2. If the pull request adds functionality, the docs should be updated. Put
   your new functionality into a function with a docstring, and add the
   feature to the list in README.rst.
3. The pull request should work for Python 2.6, 2.7, 3.3, 3.4 and 3.5, and for PyPy. Check
   https://travis-ci.org/asarao/qa/pull_requests
   and make sure that the tests pass for all supported Python versions.
    * Not all python versions are currently supported
    * Python 2.7 is fully supported

Tips
----

To run a subset of tests::
    $ python -m unittest tests.test_qa
