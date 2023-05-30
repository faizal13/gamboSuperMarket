package com.gamboSupermarket.application.model;

public class RequestConfiguration {

	private Integer connectionRequestTimeout;
	private Integer connectTimeout;
	private Integer socketTimeout;

	RequestConfiguration(Integer connectionRequestTimeout, Integer connectTimeout, Integer socketTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
		this.connectTimeout = connectTimeout;
		this.socketTimeout = socketTimeout;
	}

	public static RequestConfigurationBuilder builder() {
		return new RequestConfigurationBuilder();
	}

	public Integer getConnectionRequestTimeout() {
		return this.connectionRequestTimeout;
	}

	public Integer getConnectTimeout() {
		return this.connectTimeout;
	}

	public Integer getSocketTimeout() {
		return this.socketTimeout;
	}

	public String toString() {
		return "RequestConfiguration(connectionRequestTimeout=" + this.getConnectionRequestTimeout()
				+ ", connectTimeout=" + this.getConnectTimeout() + ", socketTimeout=" + this.getSocketTimeout() + ")";
	}

	public static class RequestConfigurationBuilder {
		private Integer connectionRequestTimeout;
		private Integer connectTimeout;
		private Integer socketTimeout;

		RequestConfigurationBuilder() {
		}

		public RequestConfiguration.RequestConfigurationBuilder connectionRequestTimeout(
				Integer connectionRequestTimeout) {
			this.connectionRequestTimeout = connectionRequestTimeout;
			return this;
		}

		public RequestConfiguration.RequestConfigurationBuilder connectTimeout(Integer connectTimeout) {
			this.connectTimeout = connectTimeout;
			return this;
		}

		public RequestConfiguration.RequestConfigurationBuilder socketTimeout(Integer socketTimeout) {
			this.socketTimeout = socketTimeout;
			return this;
		}

		public RequestConfiguration build() {
			return new RequestConfiguration(connectionRequestTimeout, connectTimeout, socketTimeout);
		}

		public String toString() {
			return "RequestConfiguration.RequestConfigurationBuilder(connectionRequestTimeout="
					+ this.connectionRequestTimeout + ", connectTimeout=" + this.connectTimeout + ", socketTimeout="
					+ this.socketTimeout + ")";
		}
	}
}
