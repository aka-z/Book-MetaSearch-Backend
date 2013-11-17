Book-MetaSearch-Backend
=======================

The Book MetaSearch App uses this service as both an API interface to other services, and
as a processing & caching layer for some inventory providers who do not make an API available.

This project contains several modules, each with a different function related to the Book MetaSearch App.

- Book-MetaSearch-Api

   A RESTful web service to expose the data cached locally as well as translations of 3rd-party APIs

- Book-MetaSearch-Audible-Data-Cacher

   A service designed to be run in a cron job to download the weekly lists from Audible, process
   the data, and store in a local database for consumption by the Book-MetaSearch-Api.

More services will be added as this project grows.