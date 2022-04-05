/* tslint:disable */
/* eslint-disable */
/**
 * Thing-People-List Api
 * Public API of the Thing-People-List Service. Thing-People-List is a Web-Service to keep track of which people did what i.e. who borrowed what from you.
 *
 * The version of the OpenAPI document: 2.0.0
 * Contact: dev@finn-thorben.me
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import * as runtime from "../runtime";
import type { Problem, ThingListDto } from "../models";

import {
  ProblemFromJSON,
  ProblemToJSON,
  ThingListDtoFromJSON,
  ThingListDtoToJSON,
} from "../models";

export interface Create2Request {
  thingListDto: ThingListDto;
}

export interface GetByName1Request {
  name: string;
}

/**
 *
 */
export class ThingListControllerApi extends runtime.BaseAPI {
  /**
   * Create a new list
   */
  async create2Raw(
    requestParameters: Create2Request,
    initOverrides?: RequestInit
  ): Promise<runtime.ApiResponse<ThingListDto>> {
    if (
      requestParameters.thingListDto === null ||
      requestParameters.thingListDto === undefined
    ) {
      throw new runtime.RequiredError(
        "thingListDto",
        "Required parameter requestParameters.thingListDto was null or undefined when calling create2."
      );
    }

    const queryParameters: any = {};

    const headerParameters: runtime.HTTPHeaders = {};

    headerParameters["Content-Type"] = "application/json";

    if (this.configuration && this.configuration.apiKey) {
      headerParameters["Authorization"] =
        this.configuration.apiKey("Authorization"); // token authentication
    }

    const response = await this.request(
      {
        path: `/api/lists/`,
        method: "POST",
        headers: headerParameters,
        query: queryParameters,
        body: ThingListDtoToJSON(requestParameters.thingListDto),
      },
      initOverrides
    );

    return new runtime.JSONApiResponse(response, (jsonValue) =>
      ThingListDtoFromJSON(jsonValue)
    );
  }

  /**
   * Create a new list
   */
  async create2(
    requestParameters: Create2Request,
    initOverrides?: RequestInit
  ): Promise<ThingListDto> {
    const response = await this.create2Raw(requestParameters, initOverrides);
    return await response.value();
  }

  /**
   * List all lists
   */
  async getAll1Raw(
    initOverrides?: RequestInit
  ): Promise<runtime.ApiResponse<Array<ThingListDto>>> {
    const queryParameters: any = {};

    const headerParameters: runtime.HTTPHeaders = {};

    if (this.configuration && this.configuration.apiKey) {
      headerParameters["Authorization"] =
        this.configuration.apiKey("Authorization"); // token authentication
    }

    const response = await this.request(
      {
        path: `/api/lists/`,
        method: "GET",
        headers: headerParameters,
        query: queryParameters,
      },
      initOverrides
    );

    return new runtime.JSONApiResponse(response, (jsonValue) =>
      jsonValue.map(ThingListDtoFromJSON)
    );
  }

  /**
   * List all lists
   */
  async getAll1(initOverrides?: RequestInit): Promise<Array<ThingListDto>> {
    const response = await this.getAll1Raw(initOverrides);
    return await response.value();
  }

  /**
   * Retrieve information about a specific list
   */
  async getByName1Raw(
    requestParameters: GetByName1Request,
    initOverrides?: RequestInit
  ): Promise<runtime.ApiResponse<ThingListDto>> {
    if (
      requestParameters.name === null ||
      requestParameters.name === undefined
    ) {
      throw new runtime.RequiredError(
        "name",
        "Required parameter requestParameters.name was null or undefined when calling getByName1."
      );
    }

    const queryParameters: any = {};

    const headerParameters: runtime.HTTPHeaders = {};

    if (this.configuration && this.configuration.apiKey) {
      headerParameters["Authorization"] =
        this.configuration.apiKey("Authorization"); // token authentication
    }

    const response = await this.request(
      {
        path: `/api/lists/{name}`.replace(
          `{${"name"}}`,
          encodeURIComponent(String(requestParameters.name))
        ),
        method: "GET",
        headers: headerParameters,
        query: queryParameters,
      },
      initOverrides
    );

    return new runtime.JSONApiResponse(response, (jsonValue) =>
      ThingListDtoFromJSON(jsonValue)
    );
  }

  /**
   * Retrieve information about a specific list
   */
  async getByName1(
    requestParameters: GetByName1Request,
    initOverrides?: RequestInit
  ): Promise<ThingListDto> {
    const response = await this.getByName1Raw(requestParameters, initOverrides);
    return await response.value();
  }
}